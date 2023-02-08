package ru.practicum.ewn.service.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiErrorHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class})
    public ResponseEntity<ApiError> handleException(Exception e) {
        log.warn(e.getMessage());
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ApiError apiError = ApiError.builder()
                .status(status)
                .message(e.getMessage())
                .reason("Incorrectly made request.")
                .build();
        return ResponseEntity.status(status).body(apiError);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e) {
        log.warn(e.getMessage());

        final HttpStatus status = HttpStatus.NOT_FOUND;
        final ApiError apiError = ApiError.builder()
                .status(status)
                .message(e.getMessage())
                .reason("The required object was not found.")
                .build();
        return ResponseEntity.status(status).body(apiError);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({DataValidationException.class,
            DataIntegrityViolationException.class})
    public ResponseEntity<ApiError> handleConflictException(Exception e) {
        log.warn(e.getMessage());

        final HttpStatus status = HttpStatus.CONFLICT;
        final ApiError apiError = ApiError.builder()
                .status(status)
                .message(e.getMessage())
                .reason("For the requested operation the conditions are not met.")
                .build();
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleThrowable(final Throwable e) {
        String error = "Internal server error";
        log.error(error);
        e.printStackTrace();

        return error;
    }
}
