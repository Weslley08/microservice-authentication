package br.com.microservice.authentication.repository;

import br.com.microservice.authentication.mapper.UserMapper;
import br.com.microservice.authentication.model.dto.UserDto;
import br.com.microservice.authentication.model.entities.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    private final UserMapper userMapper = UserMapper.REFERENCE;
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void setUp() {
        UserDto userDto = new UserDto(
                "a7ea6e8d-f08a-4033-b93c-7bbd6daa6bf9", "mock_teste_1", "123456Ab*"
        );
        this.userRepository.save(this.userMapper.toEntity(userDto));
    }

    @Test
    void it_should_find_user_byId() {
        Optional<UserEntity> responseDB = this.userRepository
                .findById("a7ea6e8d-f08a-4033-b93c-7bbd6daa6bf9");
        assertFalse(responseDB.isEmpty());
    }

    @Test
    void it_should_find_user_byUsername() {
        Optional<UserEntity> responseDB = this.userRepository.findByUsername("mock_teste_1");
        assertFalse(responseDB.isEmpty());
    }
}