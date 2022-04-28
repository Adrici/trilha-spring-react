package com.devsuperior.dscatalog.services.exception;

import com.devsuperior.dscatalog.resources.exceptions.StanderError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
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


}
