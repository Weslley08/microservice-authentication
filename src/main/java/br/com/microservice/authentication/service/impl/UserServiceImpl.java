package br.com.microservice.authentication.service.impl;

import br.com.microservice.authentication.helper.ValidateHelper;
import br.com.microservice.authentication.mapper.UserMapper;
import br.com.microservice.authentication.model.ResponseData;
import br.com.microservice.authentication.model.dto.UserDto;
import br.com.microservice.authentication.model.entities.UserEntity;
import br.com.microservice.authentication.model.enums.Role;
import br.com.microservice.authentication.model.enums.TypeUpdate;
import br.com.microservice.authentication.model.request.UpdateRequest;
import br.com.microservice.authentication.repository.UserRepository;
import br.com.microservice.authentication.service.SecurityService;
import br.com.microservice.authentication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.microservice.authentication.mapper.ModelUtilsMapper.setResponseData;
import static br.com.microservice.authentication.model.enums.Role.ADMIN;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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
    }

    @Override
    public ResponseEntity<ResponseData> createUser(UserDto userDto) {
        validateHelper.verifyNotExists(userDto.getUsername());
        validateHelper.verifyPasswordForce(userDto.getPassword());

        UserEntity user = userMapper.toEntity(userDto);
        securityService.encodedPasswordUser(user, userDto.getPassword());
        userRepository.save(user);
        return new ResponseEntity<>(setResponseData(userDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseData> update(String userId,
                                               String idOperador,
                                               UpdateRequest updateRequest,
                                               TypeUpdate typeUpdate) {
        UserDto data = new UserDto();
        switch (typeUpdate) {
            case CHANGE_USER -> data = changeUser(userId, updateRequest.getUsername());
            case DEACTIVATE_USER -> data = deactivateUser(userId);
            case CHANGE_PASSWORD -> data = changePassword(userId, updateRequest.getNewPass());
            case CHANGE_ROLE -> data = changeRole(userId, idOperador);
        }
        return ResponseEntity.ok(setResponseData(data));
    }

    @Override
    public ResponseEntity<ResponseData> findById(String userId) {
        UserEntity user = validateHelper.verifyIfExists(userId);
        return ResponseEntity.ok(setResponseData(userMapper.toDto(user)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = validateHelper.verifyIfExistsUsername(username);
        return new User(user.getUsername(), user.getPassword(), securityService.getAuthority(user));
    }


    private UserDto changeUser(String userId, String newUsername) {
        UserEntity user = validateHelper.verifyIfExists(userId);

        UserDto userDto = userMapper.toDto(user);
        userDto.setUsername(newUsername);

        user = userMapper.toEntity(userDto);
        userRepository.save(user);

        return userDto;
    }

    private UserDto changePassword(String userId, String newPass) {
        UserEntity user = validateHelper.verifyIfExists(userId);

        securityService.encodedPasswordUser(user, newPass);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    private UserDto changeRole(String userId, String idOperador) {
        validateHelper.verifyRoleOperador(idOperador);
        UserEntity user = validateHelper.verifyIfExists(userId);

        Optional<UserDto> userDto = Optional.ofNullable(userMapper.toDto(user));
        userDto.ifPresent(u -> {
            switch (u.getRole()) {
                case ADMIN -> u.setRole(Role.USER);
                case USER -> u.setRole(ADMIN);
            }
        });

        UserDto retorno = userDto.orElseThrow();
        user = userMapper.toEntity(retorno);

        userRepository.save(user);
        return retorno;
    }

    private UserDto deactivateUser(String userId) {
        UserEntity userEntity = validateHelper.verifyIfExists(userId);

        UserDto userDto = userMapper.toDto(userEntity);
        userDto.setIsInactive(Boolean.TRUE);

        userEntity = userMapper.toEntity(userDto);

        userRepository.save(userEntity);
        return userDto;
    }

}
