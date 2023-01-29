package ru.practicum.ewm.stat.service.server.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class,
            BindException.class})
    public ErrorMessage handleException(BindException ex) {
        String error = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        log.error("{} \n{}", error, String.valueOf(ex));

        return new ErrorMessage(HttpStatus.BAD_REQUEST, error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingRequestHeaderException.class,
            MissingServletRequestParameterException.class})
    public ErrorMessage handleException(Exception ex) {
        String error = ex.getMessage();
        log.error("{} \n{}", error, String.valueOf(ex));

        return new ErrorMessage(HttpStatus.BAD_REQUEST, error);
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
