package com.example.demoactivity.query;

import lombok.Value;

@Value
public class GetActivityAggregateQuery {
    private final String postAggregateId;
    private final Long expectedVersion;

    
}