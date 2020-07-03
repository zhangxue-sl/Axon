package com.example.demoactivity.event;

import java.util.Date;

import lombok.Value;

@Value
public class ActivityCreateEvent {
    private final String id;
    private final String name;
    private final Date startDate;
    private final String content;
    private final Integer count;
    private final String palceId;
}