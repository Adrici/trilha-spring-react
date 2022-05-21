package com.devsuperior.dscatalog.services.exception;

import com.devsuperior.dscatalog.resources.exceptions.StanderError;
import com.devsuperior.dscatalog.resources.exceptions.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundExeception.class)
    public ResponseEntity<StanderError> entityNotFound(ResourceNotFoundExeception e,
                                                       HttpServletRequest request){
        StanderError err = new StanderError();
        err.setTimestamp(Instant.now());
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setError("Resource not found");
        err.setMessage((e.getMessage()));
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataBaseExeception.class)
    public ResponseEntity<StanderError> database(DataBaseExeception e, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StanderError err = new StanderError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Database Execption");
        err.setMessage((e.getMessage()));
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation (MethodArgumentNotValidException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; //422 http
       //objeto de resposta especial
        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Validation Execption");
        err.setMessage((e.getMessage()));
        err.setPath(request.getRequestURI());


        for (FieldError f : e.getBindingResult().getFieldErrors()){
            err.addError(f.getField(), f.getDefaultMessage());

        }

        return ResponseEntity.status(status).body(err);
    }


}
