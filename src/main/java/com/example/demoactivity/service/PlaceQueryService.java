package com.example.demoactivity.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.example.demoactivity.aggregate.PlaceAggregate;
import com.example.demoactivity.domian.Place;
import com.example.demoactivity.event.EventRecord;
import com.example.demoactivity.query.GetPlaceAggregateQuery;
import com.example.demoactivity.repository.PlaceRepository;

import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceQueryService {
    @Autowired
    private EventStore eventStore;

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private PlaceRepository placeRepository;

    public List<Object> listEventsForPlace(String placeId){
        return eventStore.readEvents(placeId).asStream().map(e -> new EventRecord(e.getPayloadType().getName(), e.getSequenceNumber(),e.getPayload()))
        .collect(Collectors.toList());
        
    }
    
    public long countEvents(String placeId){
         return eventStore.readEvents(placeId).asStream().count();
    }

    public Optional<Place> findPlace(String placeId){

        return placeRepository.findById(placeId);
    }

    public CompletableFuture<Optional<PlaceAggregate>> getPlaceAggreate(String postAggregateId, Long expectedVersion){
        return queryGateway.query(new GetPlaceAggregateQuery(postAggregateId, expectedVersion) , ResponseTypes.optionalInstanceOf(PlaceAggregate.class));

    }

    @QueryHandler
    public CompletableFuture<PlaceAggregate> handle(GetPlaceAggregateQuery getPlaceAggregateQuery){
        CompletableFuture<PlaceAggregate> future = new CompletableFuture<PlaceAggregate>();
        if(getPlaceAggregateQuery.getExpectedVersion()==null){
            EventSourcingRepository.builder(PlaceAggregate.class).eventStore(eventStore).build()
            .load(getPlaceAggregateQuery.getPostAggregateId()).execute(future::complete);
        }
        else{
            EventSourcingRepository.builder(PlaceAggregate.class).eventStore(eventStore).eventStreamFilter(event->event.getSequenceNumber()<=getPlaceAggregateQuery.getExpectedVersion()).build()
            .load(getPlaceAggregateQuery.getPostAggregateId()).execute(future::complete);
        }
        return future;
    }
}