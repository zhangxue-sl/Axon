package com.example.demoactivity.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;
/**
 * 修改place命令
 */
@Value
public class UpdatePlaceCommand {
    @TargetAggregateIdentifier
    private final String id;
    private final String name;
}