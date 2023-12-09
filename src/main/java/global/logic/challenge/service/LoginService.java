package global.logic.challenge.service;

import global.logic.challenge.dto.SignUpRequestDTO;
import global.logic.challenge.dto.UserLoginResponseDTO;

public interface LoginService {

    UserLoginResponseDTO signUp(SignUpRequestDTO signUpRequestDTO);
    UserLoginResponseDTO login( String authorization);
}
