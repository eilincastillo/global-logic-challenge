package global.logic.challenge.controller;

import global.logic.challenge.dto.SignUpRequestDTO;
import global.logic.challenge.dto.UserLoginResponseDTO;
import global.logic.challenge.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("")
@Validated
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping(path = {"/sign-up"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponseDTO> signUp(SignUpRequestDTO signUpRequestDTO) {
        UserLoginResponseDTO userLoginResponseDTO = loginService.signUp(signUpRequestDTO);
        return new ResponseEntity<>(userLoginResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping(path = {"/login"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponseDTO> login(@RequestHeader("Authorization") String authorization) {
        UserLoginResponseDTO userLoginResponseDTO = loginService.login(authorization);
        return new ResponseEntity<>(userLoginResponseDTO, HttpStatus.ACCEPTED);
    }
}
