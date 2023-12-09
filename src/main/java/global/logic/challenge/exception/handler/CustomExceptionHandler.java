package global.logic.challenge.exception.handler;

import global.logic.challenge.dto.ErrorDTO;
import global.logic.challenge.dto.ErrorResponseDTO;
import global.logic.challenge.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.util.Collections;

@ControllerAdvice
@RestController
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public final ResponseEntity<ErrorResponseDTO> handleClientException(UserException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .code(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .build();
        ErrorResponseDTO responseException = ErrorResponseDTO.builder()
                .error(Collections.singletonList(errorDTO))
                .build();

        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);

    }
}
