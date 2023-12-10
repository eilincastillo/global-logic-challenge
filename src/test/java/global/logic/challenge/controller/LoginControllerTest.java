package global.logic.challenge.controller;

import global.logic.challenge.dto.SignUpRequestDTO;
import global.logic.challenge.dto.UserLoginResponseDTO;
import global.logic.challenge.service.LoginService;
import javassist.NotFoundException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class LoginControllerTest {

    @InjectMocks
    LoginController loginController;

    @Mock
    LoginService loginService;

    private final EasyRandom easyRandom = new EasyRandom();
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void signUp() {
        SignUpRequestDTO signUpRequestDTO = createSignUpRequestDTO();
        Mockito.when(loginService
                        .signUp(signUpRequestDTO))
                .thenReturn(createUserLoginResponseDTO());

        ResponseEntity<UserLoginResponseDTO> result = loginController
                .signUp(signUpRequestDTO);
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    private SignUpRequestDTO createSignUpRequestDTO() {
        return easyRandom.nextObject(SignUpRequestDTO.class);
    }

    private UserLoginResponseDTO createUserLoginResponseDTO() {
        return easyRandom.nextObject(UserLoginResponseDTO.class);
    }
    @Test
    void login() throws NotFoundException {
        String token = "tokenjdndfkkfd";
        Mockito.when(loginService
                        .login(any(String.class)))
                .thenReturn(createUserLoginResponseDTO());

        ResponseEntity<UserLoginResponseDTO> result = loginController
                .login(token);
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }
}
