package com.example.demoactivity.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
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
                // thenApply：等待上一个的结果，并用到下一个：上个任务完成
                .thenApply((a) -> activityQueryService.getActivityAggreate(updateActivityDto.getId(), null));

    }

    @GetMapping(value = "listEventsForActivity")
    public List<Object> listEventsForActivity(@RequestParam String activityId) {

        return activityQueryService.listEventsForActivity(activityId);
    }

    @GetMapping(value = "countEventsActicity")
    public Long countEventsActicity(@RequestParam String activityId) {
        return activityQueryService.countEvents(activityId);
    }

    @GetMapping(value = "getActivity")
    public Optional<Activity> getActivity(@RequestParam String activityId) {

        // Activity activity = new Activity();
        // activity.setId("1");
        // activity.setContent("11111");
        // activityRepository.save(activity);
        return activityQueryService.findActivity(activityId);
    }

    //根据id和版本号查询版本
    @GetMapping(value = "getReversionOfActivity")
    public CompletableFuture<Optional<ActivityAggregate>> getReversionOfActivity(@RequestParam String activityId,
            @RequestParam Optional<Long> expectedVersion) {
        return activityQueryService.getActivityAggreate(activityId, expectedVersion.orElse(null));
    }

    @GetMapping(value = "getActivityByName")
    public List<Activity> getActivityByName(String name) {

        return activityQueryService.findActivityByname(name);
    }

    // @GetMapping(value = "getTimeCounts")
    // public List<Activity> getTimeCounts(Date oneTime,Date secondTime){

    // // SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // return activityRepository.countByTime(oneTime,secondTime);
    // }

    @GetMapping(value = "getActivityByTime")
    public List<Activity> getActivityByTime(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date from,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to) {

        return activityRepository.findByStartDateBetween(from, to);
    }

    @GetMapping(value = "getActivityCount")
    public Long getActivityCount(@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date from,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date to) {

        return activityRepository.countByStartDateBetween(from, to);
    }

}