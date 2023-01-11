package br.com.microservice.authentication.service.impl;

import br.com.microservice.authentication.exception.ForbiddenErrorException;
import br.com.microservice.authentication.helper.ValidateHelper;
import br.com.microservice.authentication.model.dto.JwtDto;
import br.com.microservice.authentication.model.enums.TypeTokenEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedCaseInsensitiveMap;

import static br.com.microservice.authentication.helper.JwtHelper.createJwtForClaims;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Mock
    @SuppressWarnings("unused")
    private ValidateHelper validateHelper;
    @Mock
    private UserServiceImpl userDetailsService;

//    @Test
//    void it_should_authenticate_user() {
//        LoginDto loginDto = new LoginDto("mock_teste_1", "123456789");
//        when(this.userDetailsService.loadUserByUsername(loginDto.getUsername())).thenReturn(new User(
//                loginDto.getUsername(), loginDto.getPassword(),
//                Collections.singleton(new SimpleGrantedAuthority(ROLE_ + Role.USER))
//        ));
//        JwtDto jwtDto = this.authenticationService.authenticateUser(loginDto);
//        assertNotNull(jwtDto);
//    }

    @Test
    void it_should_refresh_user_token() {
        String token = createJwtForClaims(
                "mock_teste", new LinkedCaseInsensitiveMap<>(), TypeTokenEnum.REFRESH_TOKEN
        );
        JwtDto jwtDto = this.authenticationService.refreshUserToken(token);
        assertNotNull(jwtDto);
    }

    @Test
    void it_should_refresh_user_token_expired() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZXNsbGV5MDgiLCJuYmYiOjE2NzI3MTc3NDEsImV4cCI6MTY3MjcxNzc5MSwiYXV0aG9yaXRpZXMiOiJST0xFX1VTRVIiLCJ1c2VybmFtZSI6Indlc2xsZXkwOCJ9.rMUJqaA67P7Mh8BM8oX8aZO21I3Pe93APF3p9k5OZ3g";
        ForbiddenErrorException expiredException = assertThrows(
                ForbiddenErrorException.class, () -> this.authenticationService.refreshUserToken(token)
        );
        assertNotNull(expiredException);
    }
}