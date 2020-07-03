package com.example.demoactivity.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.example.demoactivity.aggregate.ActivityAggregate;
import com.example.demoactivity.domian.Activity;
import com.example.demoactivity.event.EventRecord;
import com.example.demoactivity.query.GetActivityAggregateQuery;
import com.example.demoactivity.repository.ActivityRepository;

import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityQueryService {
    @Autowired
    private EventStore eventStore;

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private ActivityRepository activityRepository;

    public List<Object> listEventsForActivity(String activityId){
        return eventStore.readEvents(activityId).asStream().map(e -> new EventRecord(e.getPayloadType().getName(), e.getSequenceNumber(),e.getPayload()))
        .collect(Collectors.toList());
        
    }
    
    public long countEvents(String activityId){
         return eventStore.readEvents(activityId).asStream().count();
    }

    public Optional<Activity> findActivity(String activityId){

        return activityRepository.findById(activityId);
    }

    public CompletableFuture<Optional<ActivityAggregate>> getActivityAggreate(String postAggregateId, Long expectedVersion){
        return queryGateway.query(new GetActivityAggregateQuery(postAggregateId, expectedVersion) , ResponseTypes.optionalInstanceOf(ActivityAggregate.class));

    }

    @QueryHandler
    public CompletableFuture<ActivityAggregate> handle(GetActivityAggregateQuery getActivityAggregateQuery){
        CompletableFuture<ActivityAggregate> future = new CompletableFuture<ActivityAggregate>();
        if(getActivityAggregateQuery.getExpectedVersion()==null){
            EventSourcingRepository.builder(ActivityAggregate.class).eventStore(eventStore).build()
            .load(getActivityAggregateQuery.getPostAggregateId()).execute(future::complete);
        }
        else{
            EventSourcingRepository.builder(ActivityAggregate.class).eventStore(eventStore).eventStreamFilter(event->event.getSequenceNumber()<=getActivityAggregateQuery.getExpectedVersion()).build()
            .load(getActivityAggregateQuery.getPostAggregateId()).execute(future::complete);
        }
        return future;
    }
}