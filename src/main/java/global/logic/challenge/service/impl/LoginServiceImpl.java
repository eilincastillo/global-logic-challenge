package global.logic.challenge.service.impl;

import global.logic.challenge.dto.SignUpRequestDTO;
import global.logic.challenge.dto.UserLoginResponseDTO;
import global.logic.challenge.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    @Override
    public UserLoginResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) {
        return null;
    }

    @Override
    public UserLoginResponseDTO login(String authorization) {
        return null;
    }
}
