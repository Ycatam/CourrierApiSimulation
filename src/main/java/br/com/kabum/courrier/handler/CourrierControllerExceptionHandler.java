package br.com.kabum.courrier.handler;

import br.com.kabum.courrier.rest.Error;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class that handles errors that can happen in the controller class, building the error message, and prevents the use
 * of try-catch blocks to handle exceptions, building a message instead of printing the entire stack trace.
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class CourrierControllerExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error httpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException){
        return Error.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(httpMessageNotReadableException.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        return Error.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(methodArgumentNotValidException.getMessage())
                .build();
    }

    @ExceptionHandler(JDBCConnectionException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public Error jdbcConnectionException(JDBCConnectionException jdbcConnectionException){
        return Error.builder()
                .httpStatusCode(HttpStatus.SERVICE_UNAVAILABLE)
                .message(jdbcConnectionException.getMessage())
                .build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Error nullPointerException(NullPointerException nullPointerException){
        return Error.builder()
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(nullPointerException.getMessage())
                .build();
    }
}
