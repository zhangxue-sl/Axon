package com.example.demoactivity.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

/**
 * 创建place命令
 */
@Value
public class CreatePlaceCommand {
 
    @TargetAggregateIdentifier
    private final String id;
    private final String name;



}