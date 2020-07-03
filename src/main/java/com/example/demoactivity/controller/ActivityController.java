package com.example.demoactivity.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.example.demoactivity.aggregate.ActivityAggregate;
import com.example.demoactivity.domian.Activity;
import com.example.demoactivity.dto.CreateActivityDto;
import com.example.demoactivity.dto.UpdateActivityDto;
import com.example.demoactivity.repository.ActivityRepository;
import com.example.demoactivity.service.ActivityCommandService;
import com.example.demoactivity.service.ActivityQueryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {
    @Autowired
    private ActivityCommandService activityCommandService;

    @Autowired
    private ActivityQueryService activityQueryService;

    @Autowired
    ActivityRepository activityRepository;

    @PostMapping(value = "createActivity")
    public CompletableFuture<String> createActivity(@RequestBody CreateActivityDto createActivityDto) {
        return activityCommandService.createActivity(createActivityDto);

    }

    @PostMapping(value = "updateActivity")
    public CompletableFuture<Object> updateActivity(@RequestBody UpdateActivityDto updateActivityDto) {
        return activityCommandService.updateActivity(updateActivityDto)
                .thenApply((a) -> activityQueryService.getActivityAggreate(updateActivityDto.getId(), null));

    }

    /////////// 404
    @GetMapping(value = "listEventsForActicity")
    public List<Object> listEventsForActicity(@RequestParam String activityId) {

        return activityQueryService.listEventsForActivity(activityId);
    }

    @GetMapping(value = "countEventsActicity")
    public Long countEventsActicity(@RequestParam String activityId) {
        return activityQueryService.countEvents(activityId);
    }

    //////////// 为空。原因是没存进数据库
    @GetMapping(value = "getActivity")
    public Optional<Activity> getActivity(@RequestParam String activityId) {

        Activity activity = new Activity();
        activity.setId("1");
        activity.setContent("11111");
        activityRepository.save(activity);



        return activityQueryService.findActivity(activityId);
    }

    @GetMapping(value = "getReversionOfActivity")
    public CompletableFuture<Optional<ActivityAggregate>> getReversionOfActivity(@RequestParam String activityId,
            @RequestParam Optional<Long> expectedVersion) {
        return activityQueryService.getActivityAggreate(activityId, expectedVersion.orElse(null));
    }

}