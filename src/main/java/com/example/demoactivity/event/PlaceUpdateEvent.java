package com.example.demoactivity.event;



import lombok.Value;

@Value
public class PlaceUpdateEvent {
    private final String id;
    private final String name;
}