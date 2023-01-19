package br.com.microservice.authentication.service.impl;

import br.com.microservice.authentication.helper.ValidateHelper;
import br.com.microservice.authentication.mapper.UserMapper;
import br.com.microservice.authentication.model.dto.UserDto;
import br.com.microservice.authentication.model.entities.UserEntity;
import br.com.microservice.authentication.model.enums.FindTypeEnum;
import br.com.microservice.authentication.model.enums.RoleEnum;
import br.com.microservice.authentication.model.enums.TypeUpdateEnum;
import br.com.microservice.authentication.model.request.UpdateRequest;
import br.com.microservice.authentication.repository.UserRepository;
import br.com.microservice.authentication.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {

    private final UserMapper userMapper = UserMapper.REFERENCE;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private ValidateHelper validateHelper;
    @Mock
    @SuppressWarnings("unused")
    private SecurityService securityService;
    @Mock
    @SuppressWarnings("unused")
    private UserRepository userRepository;
    private UserEntity userEntity;
    private UserEntity userEntityDeactivate;

    @BeforeEach
    void setUp() {
        createEntity();
        createEntityDeactivate();
    }

    @Test
    void it_should_create_user() {
        UserDto userDto = new UserDto(
                UUID.randomUUID().toString(), "mock_teste_create", "123456Ab*"
        );
        UserDto retorno = this.userService.createUser(userDto);
        assertEquals(userDto.getUserId(), retorno.getUserId());
    }

    @Test
    void it_should_update_username() {
        UserDto retorno = this.userService.update(
                new UpdateRequest("mock_teste_update_user", "123456Ab*", RoleEnum.ADMIN),
                TypeUpdateEnum.CHANGE_USER);
        assertNotNull(retorno);
    }


    @Test
    void it_should_update_password() {
        UpdateRequest updateRequest = new UpdateRequest("mock_teste_1", "123456Ab*", RoleEnum.USER);
        UserDto retorno = this.userService.update(updateRequest,
                TypeUpdateEnum.CHANGE_PASSWORD);
        assertEquals(updateRequest.getNewPass(), retorno.getPassword());
    }

    @Test
    void it_should_update_role() {
        UserDto retorno = this.userService.update(
                new UpdateRequest("mock_teste_update_user", "123456Ab*", RoleEnum.ADMIN),
                TypeUpdateEnum.CHANGE_ROLE);
        assertEquals(RoleEnum.ADMIN, retorno.getRole());
    }

    @Test
    void it_should_update_not_reset_or_reset_password() {
        when(this.validateHelper.verifyIfExists("a7ea6e8d-f08a-4033-b93c-7bbd6daa6bf9"))
                .thenReturn(this.userEntity);
        UserDto retorno = this.userService.update(
                new UpdateRequest("mock_teste_update_user", "123456Ab*", RoleEnum.USER),
                TypeUpdateEnum.NOT_RESET_PASSWORD_OR_RESET_PASSWORD);
        assertTrue(retorno.getNotResetPassword());
    }


    @Test
    void it_should_update_deactivate_or_activate_user() {
        UserDto retorno = this.userService.update(
                new UpdateRequest("mock_teste_update_user", "123456Ab*", RoleEnum.USER),
                TypeUpdateEnum.DEACTIVATE_OR_ACTIVATE_USER);
        UserEntity userEntity = this.userEntity;
        assertEquals(userEntity.getNotResetPassword(), retorno.getNotResetPassword());
    }

    @Test
    void it_should_find_user_byId() {
        UserDto userDto = this.userService.find(FindTypeEnum.USER_ID);
        assertNotNull(userDto);
    }

    @Test
    void it_should_find_user_byUsername() {
        when(this.userRepository.findByUsername(this.userEntity.getUsername())).
                thenReturn(Optional.ofNullable(this.userEntity));
        when(validateHelper.verifyIfExistsUsername(this.userEntity.getUsername()))
                .thenReturn(this.userEntity);
        UserDto userDto = this.userService.find(FindTypeEnum.USERNAME);
        assertNotNull(userDto);
    }


    @Test
    void it_should_load_user_ByUsername() {
        when(validateHelper.verifyIfExistsUsername(this.userEntity.getUsername()))
                .thenReturn(this.userEntity);

        UserDetails userDetails = this.userService.loadUserByUsername("mock_teste_1");
        assertNotNull(userDetails);
    }

    @Test
    void it_should_load_user_deactivate() {
        when(this.userRepository.findByUsername(this.userEntityDeactivate.getUsername())).
                thenReturn(Optional.ofNullable(this.userEntityDeactivate));
        when(validateHelper.verifyIfExistsUsername(this.userEntityDeactivate.getUsername()))
                .thenReturn(this.userEntityDeactivate);
        UserDetails userDetails = this.userService.loadUserByUsername(this.userEntityDeactivate.getUsername());
        assertFalse(userDetails.isEnabled());
    }

    private void createEntity() {
        UserDto userDto = new UserDto(
                "a7ea6e8d-f08a-4033-b93c-7bbd6daa6bf9", "mock_teste_1", "123456Ab*"
        );
        this.userEntity = this.userMapper.toEntity(userDto);
    }

    private void createEntityDeactivate() {
        UserDto userDto = new UserDto(
                "a7ea6e8d-f08a-4033-b93c-7bbd6daa6bfb", "mock_teste_2", "123456Ab*"
        );
        userDto.setIsInactive(Boolean.TRUE);
        this.userEntityDeactivate = this.userMapper.toEntity(userDto);
    }
}