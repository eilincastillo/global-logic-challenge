package global.logic.challenge.service.impl;

import global.logic.challenge.dto.SignUpRequestDTO;
import global.logic.challenge.dto.UserLoginResponseDTO;
import global.logic.challenge.exception.UserException;
import global.logic.challenge.model.Phone;
import global.logic.challenge.model.User;
import global.logic.challenge.repository.PhoneRepository;
import global.logic.challenge.repository.UserRepository;
import global.logic.challenge.util.JwtUtil;
import javassist.NotFoundException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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

        assertThrows(UserException.class, () -> loginService.signUp(signUpRequestDTO));
    }

    @Test
    void login_Success() throws NotFoundException {
        String fakeToken = JwtUtil.generateToken("test@example.com");
        User user = createUser();
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        Mockito.when(phoneRepository.findByUser(any())).thenReturn(createPhone_user(user));

        UserLoginResponseDTO responseDTO = loginService.login("Bearer "+ fakeToken);

        assert(responseDTO.getEmail()).equals("test@example.com");
    }
    @Test
    void login_Not_Found() throws NotFoundException {
        String fakeToken = JwtUtil.generateToken("test@example.com");
        User user = createUser();
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        Mockito.when(phoneRepository.findByUser(any())).thenReturn(createPhone_user(user));

        assertThrows(NotFoundException.class, () -> loginService.login("Bearer "+ fakeToken));
    }

    private SignUpRequestDTO createSignUpRequestDTO() {
        return easyRandom.nextObject(SignUpRequestDTO.class);
    }

    private User createUser() {
        return User.builder().id(45445L)
                .created(new Date())
                .lastLogin(new Date())
                .email("test@example.com")
                .password("Password22")
                .isActive(Boolean.TRUE)
                .build();
    }

    private Phone createPhone() {
        return easyRandom.nextObject(Phone.class);
    }
    private List<Phone> createPhone_user(User user) {
        Phone phone = Phone.builder()
                .number(945111223L)
                .citycode(9)
                .countrycode(56)
                .user(user)
                .build();
        return Collections.singletonList(phone);
    }
}
