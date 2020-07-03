package com.example.demoactivity.command;

import java.util.Date;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

/**
 * 创建Activity命令
 */
@Value
public class CreateActivityCommand {
    @TargetAggregateIdentifier
    private final String id;
    private final Date startDate;
    private final String content;
    private final Integer count;
    private final String palceId;

    
}