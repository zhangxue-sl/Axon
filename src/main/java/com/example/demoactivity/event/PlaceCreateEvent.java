package com.example.demoactivity.event;

import lombok.Value;

@Value
public class PlaceCreateEvent {
    private final String id;
    private final String name;
    
}