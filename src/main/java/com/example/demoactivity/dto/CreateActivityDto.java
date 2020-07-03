package com.example.demoactivity.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CreateActivityDto {
    private  Date startDate;
    private  String content;
    private  Integer count;
    private  String palceId;
}