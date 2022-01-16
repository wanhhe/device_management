package com.sicau.devicemanagement.exception;

import lombok.Data;

@Data
public class CaptchaException extends Throwable {
    private int code;
    private String meg;


    public CaptchaException(String msg){
        this.meg = msg;
    }
}
