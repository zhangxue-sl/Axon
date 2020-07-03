package com.example.demoactivity.event;

import java.util.Date;

import lombok.Value;

@Value
public class ActivityUpdateEvent {
    private final String id;
    private final Date startDate;
    private final String content;
    private final Integer count;
    private final String palceId;
}