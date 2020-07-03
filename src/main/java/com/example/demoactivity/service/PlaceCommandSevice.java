package com.example.demoactivity.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.example.demoactivity.command.CreatePlaceCommand;
import com.example.demoactivity.command.UpdatePlaceCommand;
import com.example.demoactivity.domian.Place;
import com.example.demoactivity.dto.CreatePlaceDto;
import com.example.demoactivity.dto.UpdatePlaceDto;
import com.example.demoactivity.event.PlaceCreateEvent;
import com.example.demoactivity.event.PlaceUpdateEvent;
import com.example.demoactivity.repository.PlaceRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceCommandSevice {
    @Autowired
   private PlaceRepository placeRepository;

    @Autowired
    private CommandGateway commandGateway;

    public CompletableFuture<String> createPlace(CreatePlaceDto createPlaceDto){

        return commandGateway.send(new CreatePlaceCommand(UUID.randomUUID().toString(),createPlaceDto.getName()));

    }
    public CompletableFuture<Void> updatePlace(UpdatePlaceDto updatePlaceDto){

        return commandGateway.send(new UpdatePlaceCommand(updatePlaceDto.getId(),updatePlaceDto.getName()));
    }

    @EventHandler
    void handle(PlaceCreateEvent event){
        Place place = new Place();
        place.setId(event.getId());
        place.setName(event.getName());
        placeRepository.save(place);

    }
    @EventHandler
    void handle(PlaceUpdateEvent event){
        Place place = placeRepository.findById(event.getId()).get();
        place.setName(event.getName());
        placeRepository.save(place);
    }

}