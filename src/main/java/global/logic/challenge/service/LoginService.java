package global.logic.challenge.service;

import global.logic.challenge.dto.SignUpRequestDTO;
import global.logic.challenge.dto.UserLoginResponseDTO;
import javassist.NotFoundException;

public interface LoginService {

    UserLoginResponseDTO signUp(SignUpRequestDTO signUpRequestDTO);
    UserLoginResponseDTO login( String authorization) throws NotFoundException;
}
