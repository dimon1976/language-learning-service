package by.languagelearningservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
@Slf4j
@ControllerAdvice
public class ExController {

     public static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }
    @ExceptionHandler
    protected ResponseEntity<Object> handleMethodRuntimeException(RuntimeException runtimeException) {
        log.warn(String.format("Bad request by %s",runtimeException.getMessage()));
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        log.warn(String.format("Bad request by %s",constraintViolationException.getMessage()));
        return new ResponseEntity<>(constraintViolationException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
