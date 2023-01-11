package br.com.microservice.authentication.service.impl;

import br.com.microservice.authentication.exception.ForbiddenErrorException;
import br.com.microservice.authentication.helper.ValidateHelper;
import br.com.microservice.authentication.mapper.UserMapper;
import br.com.microservice.authentication.model.UserDetailsCustom;
import br.com.microservice.authentication.model.dto.ErrorData;
import br.com.microservice.authentication.model.dto.UserDto;
import br.com.microservice.authentication.model.entities.UserEntity;
import br.com.microservice.authentication.model.enums.FindType;
import br.com.microservice.authentication.model.enums.Role;
import br.com.microservice.authentication.model.enums.TypeTokenEnum;
import br.com.microservice.authentication.model.enums.TypeUpdateEnum;
import br.com.microservice.authentication.model.request.UpdateRequest;
import br.com.microservice.authentication.repository.UserRepository;
import br.com.microservice.authentication.service.SecurityService;
import br.com.microservice.authentication.service.UserService;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

import static br.com.microservice.authentication.helper.JwtHelper.decodedJWT;
import static br.com.microservice.authentication.model.constants.BaseConstants.USERNAME;
import static br.com.microservice.authentication.model.constants.TasksErrorConstants.TOKEN_INCORRETO;
import static br.com.microservice.authentication.model.enums.TypeTokenEnum.ACCESS_TOKEN;
import static br.com.microservice.authentication.model.enums.TypeTokenEnum.RESET_PASSWORD_TOKEN;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final Map<TypeUpdateEnum, Function<UpdateRequest, UserDto>> typeUpdateMap;

    private final UserMapper userMapper;
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final ValidateHelper validateHelper;

    public UserServiceImpl(SecurityService securityService,
                           UserRepository userRepository,
                           ValidateHelper validateHelper) {
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.validateHelper = validateHelper;
        this.userMapper = UserMapper.REFERENCE;
        this.typeUpdateMap = this.initializeUpdateMap();
    }

    private static DecodedJWT getDecodedJwtAccessToken() {
        return decodedJWT(System.getProperty(String.valueOf(ACCESS_TOKEN)), ACCESS_TOKEN);
    }

    private static String getJwtResetPassToken() {
        try {
            return System.getProperty(String.valueOf(RESET_PASSWORD_TOKEN));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ForbiddenErrorException(new ErrorData(TOKEN_INCORRETO));
        }
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        validateHelper.verifyNotExists(userDto.getUsername());
        validateHelper.verifyPasswordForce(userDto.getPassword());

        UserEntity user = userMapper.toEntity(userDto);
        securityService.encodedPasswordUser(user, userDto.getPassword());
        userRepository.save(user);
        return userDto;
    }

    @Override
    public UserDto find(FindType findType) {
        UserEntity user = new UserEntity();
        if (Objects.equals(FindType.USER_ID, findType)) {
            user = validateHelper.verifyIfExists(getDecodedJwtAccessToken().getSubject());
        } else if (Objects.equals(FindType.USERNAME, findType)) {
            user = validateHelper.verifyIfExistsUsername(
                    getDecodedJwtAccessToken().getClaim(USERNAME).asString()
            );
        }
        return userMapper.toDto(user);
    }

    @Override
    public UserDetailsCustom loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = validateHelper.verifyIfExistsUsername(username);
        Set<SimpleGrantedAuthority> authorities = securityService.getAuthority(user.getRole());

        if (user.getIsInactive()) {
            return new UserDetailsCustom(
                    user.getUserId(), user.getUsername(), user.getPassword(),
                    Boolean.FALSE, Boolean.TRUE,
                    Boolean.TRUE, Boolean.FALSE, authorities
            );
        }
        return new UserDetailsCustom(user.getUserId(), user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public UserDto update(UpdateRequest updateRequest,
                          TypeUpdateEnum typeUpdateEnum) {
        return this.typeUpdateMap.get(typeUpdateEnum).apply(updateRequest);
    }

    private Map<TypeUpdateEnum, Function<UpdateRequest, UserDto>> initializeUpdateMap() {
        Map<TypeUpdateEnum, Function<UpdateRequest, UserDto>> map = new EnumMap<>(TypeUpdateEnum.class);

        map.put(
                TypeUpdateEnum.CHANGE_USER,
                request -> this.changeUsername(request.getNewUsername())
        );

        map.put(
                TypeUpdateEnum.CHANGE_PASSWORD,
                request -> this.changePassword(request.getNewPass())
        );

        map.put(
                TypeUpdateEnum.CHANGE_ROLE,
                request -> this.changeRole(request.getIdOperador(), request.getNewRole())
        );

        map.put(
                TypeUpdateEnum.NOT_RESET_PASSWORD_OR_RESET_PASSWORD,
                request -> this.notResetOrResetPassword()
        );

        map.put(
                TypeUpdateEnum.DEACTIVATE_OR_ACTIVATE_USER,
                request -> this.deactivateOrActivateUser()
        );

        return Collections.unmodifiableMap(map);
    }

    private UserDto changeUsername(String newUsername) {
        UserEntity user = validateHelper.verifyIfExists(getDecodedJwtAccessToken().getSubject());

        UserDto userDto = userMapper.toDto(user);
        userDto.setUsername(newUsername);

        user = userMapper.toEntity(userDto);
        userRepository.save(user);

        return userDto;
    }

    private UserDto changePassword(String newPass) {
        DecodedJWT jwtDecoded = decodedJWT(getJwtResetPassToken(), TypeTokenEnum.RESET_PASSWORD_TOKEN);
        validateHelper.notAllowResetPassword(jwtDecoded.getSubject());
        validateHelper.verifyPasswordForce(newPass);
        UserEntity user = validateHelper.verifyIfExists(jwtDecoded.getSubject());

        securityService.encodedPasswordUser(user, newPass);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    private UserDto changeRole(String idOperador, Role role) {
        validateHelper.verifyRoleOperador(idOperador);
        UserEntity user = validateHelper.verifyIfExists(getDecodedJwtAccessToken().getSubject());

        Optional<UserDto> userDto = Optional.ofNullable(userMapper.toDto(user));
        userDto.ifPresent(u -> u.setRole(role));

        UserDto retorno = userDto.orElseThrow();
        user = userMapper.toEntity(retorno);

        userRepository.save(user);
        return retorno;
    }

    private UserDto deactivateOrActivateUser() {
        UserEntity userEntity = validateHelper.verifyIfExists(getDecodedJwtAccessToken().getSubject());

        UserDto userDto = userMapper.toDto(userEntity);
        userDto.setIsInactive(userEntity.getIsInactive() ? Boolean.FALSE : Boolean.TRUE);

        userEntity = userMapper.toEntity(userDto);
        userRepository.save(userEntity);
        return userDto;
    }

    private UserDto notResetOrResetPassword() {
        UserEntity userEntity = validateHelper.verifyIfExists(getDecodedJwtAccessToken().getSubject());

        UserDto userDto = userMapper.toDto(userEntity);
        userDto.setNotResetPassword(userEntity.getNotResetPassword() ? Boolean.FALSE : Boolean.TRUE);

        userEntity = userMapper.toEntity(userDto);
        userRepository.save(userEntity);
        return userDto;
    }
}
