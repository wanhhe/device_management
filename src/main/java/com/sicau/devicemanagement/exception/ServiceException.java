package com.sicau.devicemanagement.exception;


import lombok.Data;

@Data
public class ServiceException extends Throwable{
    private int code;
    private String msg;

    public ServiceException(String msg){
        this.msg = msg;
    }
}
