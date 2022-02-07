package com.sicau.devicemanagement.domain.model;

import lombok.Data;

@Data
public class SicauBody {

    private BasicResponse basicResponse;

    private int num;

    private String[] resultHead;

    private String[] resultValue;
}
