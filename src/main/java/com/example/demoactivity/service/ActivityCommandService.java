package com.example.demoactivity.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.example.demoactivity.command.CreateActivityCommand;
import com.example.demoactivity.command.UpdateActivityCommand;
import com.example.demoactivity.domian.Activity;
import com.example.demoactivity.dto.CreateActivityDto;
import com.example.demoactivity.dto.UpdateActivityDto;
import com.example.demoactivity.event.ActivityCreateEvent;
import com.example.demoactivity.event.ActivityUpdateEvent;
import com.example.demoactivity.repository.ActivityRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityCommandService {
    @Autowired
   private ActivityRepository activityRepository;

    @Autowired
    private CommandGateway commandGateway;

    public CompletableFuture<String> createActivity(CreateActivityDto createActivityDto){

        return commandGateway.send(new CreateActivityCommand(UUID.randomUUID().toString(),createActivityDto.getName(),createActivityDto.getStartDate(),createActivityDto.getContent(),createActivityDto.getCount(),createActivityDto.getPalceId()));
        
    }
    public CompletableFuture<Void> updateActivity(UpdateActivityDto updateActivityDto){

        return commandGateway.send(new UpdateActivityCommand(updateActivityDto.getId(),updateActivityDto.getName(),updateActivityDto.getStartDate(),updateActivityDto.getContent(),updateActivityDto.getCount(),updateActivityDto.getPalceId()));
    }

    @EventHandler
    void handle(ActivityCreateEvent event){
        Activity activity = new Activity();
        activity.setId(event.getId());
        activity.setName(event.getName());
        activity.setStartDate(event.getStartDate());
        activity.setContent(event.getContent());
        activity.setCount(event.getCount());
        activity.setPalceId(event.getPalceId());
        activityRepository.save(activity);

    }
    @EventHandler
    void handle(ActivityUpdateEvent event){
        Activity activity=activityRepository.findById(event.getId()).get();
        activity.setName(event.getName());
        activity.setStartDate(event.getStartDate());
        activity.setContent(event.getContent());
        activity.setCount(event.getCount());
        activity.setPalceId(event.getPalceId());
        activityRepository.save(activity);
    }
    


   
}