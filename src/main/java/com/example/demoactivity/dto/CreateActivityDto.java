package com.example.demoactivity.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CreateActivityDto {
    private String name;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    //////0000
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    ) 
    private  Date startDate;
    private  String content;
    private  Integer count;
    private  String palceId;
}