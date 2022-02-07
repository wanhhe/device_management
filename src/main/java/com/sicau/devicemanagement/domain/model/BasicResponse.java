package com.sicau.devicemanagement.domain.model;

import lombok.Data;

@Data
public class BasicResponse {

    private String message;

    private int status;

    private boolean success;
}
