package global.logic.challenge.controller;

import global.logic.challenge.dto.SignUpRequestDTO;
import global.logic.challenge.dto.UserLoginResponseDTO;
import global.logic.challenge.service.LoginService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("")
@Validated
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/sign-up", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserLoginResponseDTO> signUp(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        log.info("[signUp: {}]", signUpRequestDTO.toString());
        return new ResponseEntity<>(loginService.signUp(signUpRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping(value= "/login", produces = "application/json")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestHeader("Authorization") String authorization) throws NotFoundException {
        log.info("[login: {}]", authorization);
        return new ResponseEntity<>(loginService.login(authorization), HttpStatus.ACCEPTED);
    }
}
