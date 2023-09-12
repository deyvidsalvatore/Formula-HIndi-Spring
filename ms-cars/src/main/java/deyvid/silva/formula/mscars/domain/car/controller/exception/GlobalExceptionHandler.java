package deyvid.silva.formula.mscars.domain.car.controller.exception;

import deyvid.silva.formula.mscars.domain.car.service.exceptions.DuplicateCarException;
import deyvid.silva.formula.mscars.domain.car.service.exceptions.DuplicatePilotException;
import deyvid.silva.formula.mscars.domain.car.service.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.error("Handling ResourceNotFoundException: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(LocalDateTime.now(), ex.getMessage(), HttpStatus.NOT_FOUND.value(), request.getRequestURI()));
    }

    @ExceptionHandler(DuplicateCarException.class)
    public ResponseEntity<StandardError> handleDuplicateCarException(DuplicateCarException ex, HttpServletRequest request) {
        log.error("Handling Duplicate Car Exception: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardError(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI()));
    }

    @ExceptionHandler(DuplicatePilotException.class)
    public ResponseEntity<StandardError> handleDuplicatePilotException(DuplicatePilotException ex, HttpServletRequest request) {
        log.error("Handling Duplicate Pilot Exception: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardError(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.error("Handling MethodArgumentNotValidException: " + ex.getMessage(), ex);
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorsMap(errors));
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
