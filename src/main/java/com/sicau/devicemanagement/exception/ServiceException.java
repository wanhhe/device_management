package com.sicau.devicemanagement.exception;


import lombok.Data;

@Data
public class ServiceException extends RuntimeException{
    private int code;
    private String message;

    public ServiceException(String msg){
        this.message = msg;
    }
}
