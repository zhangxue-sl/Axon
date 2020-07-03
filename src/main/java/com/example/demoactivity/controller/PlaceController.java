package com.example.demoactivity.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.example.demoactivity.aggregate.PlaceAggregate;
import com.example.demoactivity.domian.Place;
import com.example.demoactivity.dto.CreatePlaceDto;
import com.example.demoactivity.dto.UpdatePlaceDto;
import com.example.demoactivity.repository.PlaceRepository;
import com.example.demoactivity.service.PlaceCommandSevice;
import com.example.demoactivity.service.PlaceQueryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceController {
    @Autowired
    private PlaceCommandSevice placeCommandSevice;

    @Autowired
    private PlaceQueryService placeQueryService;
    @Autowired 
    private PlaceRepository placeRepositiry;

    @PostMapping(value = "createPlace")
    public CompletableFuture<String> createPlace(@RequestBody CreatePlaceDto createPlaceDto){
        return placeCommandSevice.createPlace(createPlaceDto);

    }
    @PostMapping(value = "updatePlace")
    public CompletableFuture<Object> updatePlace(@RequestBody UpdatePlaceDto updatePlaceDto){
        return placeCommandSevice.updatePlace(updatePlaceDto).thenApply((a)->placeQueryService.getPlaceAggreate(updatePlaceDto.getId(), null));
    
}

   @GetMapping(value = "listEventsForPlace")
   public List<Object> listEventsForPlace(@RequestParam String placeId){

      return placeQueryService.listEventsForPlace(placeId);
   }

   @GetMapping(value = "countEvents")
   public Long countEvents(String placeId){
       return placeQueryService.countEvents(placeId);
   }

   ///////为空，数据库为空
   @GetMapping(value = "getPlace")
   public Optional<Place> getPlace(String placeId){
       Place place=new Place();
       place.setId("1");
       place.setName("22222");
       placeRepositiry.save(place);
       return placeQueryService.findPlace(placeId);
   }

   @GetMapping(value = "getReversionOfPlace")
   public CompletableFuture<Optional<PlaceAggregate>> getReversionOfPlace( @RequestParam String placeId,@RequestParam Optional<Long>expectedVersion ){
       return placeQueryService.getPlaceAggreate(placeId, expectedVersion.orElse(null));
   }

   

}