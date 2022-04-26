package com.devsuperior.dscatalog.services.exception;

public class EntityNotFoundExeception extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public EntityNotFoundExeception(String msg){
        super(msg);
    }
}
