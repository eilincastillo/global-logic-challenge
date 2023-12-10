package global.logic.challenge.service.impl;

import global.logic.challenge.dto.SignUpRequestDTO;
import global.logic.challenge.dto.UserLoginResponseDTO;
import global.logic.challenge.exception.UserException;
import global.logic.challenge.model.Phone;
import global.logic.challenge.model.User;
import global.logic.challenge.repository.PhoneRepository;
import global.logic.challenge.repository.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class LoginServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhoneRepository phoneRepository;

    @InjectMocks
    private LoginServiceImpl loginService;

    private final EasyRandom easyRandom = new EasyRandom();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void signUp_Success() throws UserException {
        SignUpRequestDTO signUpRequestDTO = createSignUpRequestDTO();
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(any())).thenReturn(createUser());
        Mockito.when(phoneRepository.save(any())).thenReturn(createPhone());

        UserLoginResponseDTO responseDTO = loginService.signUp(signUpRequestDTO);

        assertNotNull(responseDTO);
    }

    @Test()
    void signUp_User_Exception() throws UserException {
        SignUpRequestDTO signUpRequestDTO = createSignUpRequestDTO();
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(createUser()));

        //UserLoginResponseDTO responseDTO = loginService.signUp(signUpRequestDTO);

        assertThrows(UserException.class, () -> loginService.signUp(signUpRequestDTO));
    }

    private SignUpRequestDTO createSignUpRequestDTO() {
        return easyRandom.nextObject(SignUpRequestDTO.class);
    }

    private User createUser() {
        return easyRandom.nextObject(User.class);
    }

    private Phone createPhone() {
        return easyRandom.nextObject(Phone.class);
    }
}
