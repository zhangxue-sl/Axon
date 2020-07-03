package com.example.demoactivity.dto;

import java.util.Date;

import lombok.Value;

@Value
public class UpdateActivityDto {

    private String id;
    private  Date startDate;
    private  String content;
    private  Integer count;
    private  String palceId;
}