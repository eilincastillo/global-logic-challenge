package global.logic.challenge.exception.handler;

import global.logic.challenge.dto.ErrorDTO;
import global.logic.challenge.dto.ErrorResponseDTO;
import global.logic.challenge.exception.UserException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
@RestController
@Slf4j
public class CustomExceptionHandler  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorDTO> errorList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .code(HttpStatus.BAD_REQUEST.value())
                    .detail(error.getDefaultMessage())
                    .build();
            errorList.add(errorDTO);
        });
        ErrorResponseDTO responseException = ErrorResponseDTO.builder()
                .error(errorList)
                .build();
        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }
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

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorResponseDTO> handleNotFoundtException(NotFoundException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .code(HttpStatus.NOT_FOUND.value())
                .detail(ex.getMessage())
                .build();
        ErrorResponseDTO responseException = ErrorResponseDTO.builder()
                .error(Collections.singletonList(errorDTO))
                .build();

        return new ResponseEntity<>(responseException, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .build();
        ErrorResponseDTO responseException = ErrorResponseDTO.builder()
                .error(Collections.singletonList(errorDTO))
                .build();

        return new ResponseEntity<>(responseException, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
