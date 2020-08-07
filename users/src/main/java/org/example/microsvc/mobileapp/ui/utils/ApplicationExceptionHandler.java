package org.example.microsvc.mobileapp.ui.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException ex,
                                                      WebRequest request){
        ApplicationError error = new ApplicationError();
        error.setTimestamp(new Date());
        error.setMessage(ex.getLocalizedMessage()!=null ? ex.getLocalizedMessage() : ex.toString());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex,
                                                      WebRequest request){
        ApplicationError error = new ApplicationError();
        error.setTimestamp(new Date());
        error.setMessage(ex.getLocalizedMessage()!=null ? ex.getLocalizedMessage() : ex.toString());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
