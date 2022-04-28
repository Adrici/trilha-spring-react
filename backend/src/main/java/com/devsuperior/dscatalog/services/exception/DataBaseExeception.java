package com.devsuperior.dscatalog.services.exception;

public class DataBaseExeception extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public DataBaseExeception(String msg){
        super(msg);
    }
}
