package com.sicau.devicemanagement.exception;

import lombok.Data;

@Data
public class CaptchaException extends RuntimeException {
    private int code;
    private String message;
    CaptchaException(){

    }


    public CaptchaException(String msg){
        this.message= msg;
    }
}
