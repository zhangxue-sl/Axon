package com.example.demoactivity.aggregate;

import com.example.demoactivity.command.CreatePlaceCommand;
import com.example.demoactivity.command.UpdatePlaceCommand;

import com.example.demoactivity.event.PlaceCreateEvent;
import com.example.demoactivity.event.PlaceUpdateEvent;


import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.Data;

@Aggregate
@Data
public class PlaceAggregate {
    @AggregateIdentifier
    String id;
    String name;

    protected  PlaceAggregate(){

    }

    @CommandHandler
    public PlaceAggregate(CreatePlaceCommand comm){
        AggregateLifecycle.apply(new PlaceCreateEvent(comm.getId(), comm.getName()));

    }

    @CommandHandler
    public void handle(UpdatePlaceCommand comm){
        AggregateLifecycle.apply(new PlaceUpdateEvent(comm.getId(), comm.getName()));

    }

    @EventSourcingHandler
    public void onHandle(PlaceCreateEvent event){
        this.id=event.getId();
        this.name=event.getName();
        
    }

    @EventSourcingHandler
    public void onHandle(PlaceUpdateEvent event){
        this.id=event.getId();
        this.name=event.getName();
    }

}