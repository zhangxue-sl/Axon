package com.example.demoactivity.aggregate;

import java.util.Date;

import com.example.demoactivity.command.CreateActivityCommand;
import com.example.demoactivity.command.UpdateActivityCommand;
import com.example.demoactivity.event.ActivityCreateEvent;
import com.example.demoactivity.event.ActivityUpdateEvent;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.Data;

/**
 * activity聚合类
 */
@Aggregate
@Data
public class ActivityAggregate {
    @AggregateIdentifier
     String id;
     Date startDate;
     String content;
     Integer count;
     String palceId;

     public ActivityAggregate(){}

     @CommandHandler
     public ActivityAggregate(CreateActivityCommand comm){
         AggregateLifecycle.apply(new ActivityCreateEvent(comm.getId(),comm.getStartDate(), comm.getContent(),comm.getCount(),comm.getPalceId()));
 
     }
 
     @CommandHandler
     public void handle(UpdateActivityCommand comm){
         AggregateLifecycle.apply(new ActivityUpdateEvent(comm.getId(),comm.getStartDate(),comm.getContent(),comm.getCount(),comm.getPalceId()));
 
     }
 
     @EventSourcingHandler
     public void onHandle(ActivityCreateEvent event){
         this.id=event.getId();
         this.startDate=event.getStartDate();
         this.content=event.getContent();
         this.count=event.getCount();
         this.palceId=event.getPalceId();
         
     }
 
     @EventSourcingHandler
     public void onHandle(ActivityUpdateEvent event){
         this.id=event.getId();
         this.startDate=event.getStartDate();
         this.content=event.getContent();
         this.count=event.getCount();
         this.palceId=event.getPalceId();
     }
    
}