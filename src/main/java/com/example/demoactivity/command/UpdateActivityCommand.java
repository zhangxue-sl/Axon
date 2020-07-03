package com.example.demoactivity.command;

import java.util.Date;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

/**
 * 修改activity命令
 */
@Value
public class UpdateActivityCommand {
    @TargetAggregateIdentifier
    private final String id;
    private final String  name;
    private final Date startDate;
    private final String content;
    private final Integer count;
    private final String palceId;
    
}