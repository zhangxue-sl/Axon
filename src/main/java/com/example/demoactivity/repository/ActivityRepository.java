package com.example.demoactivity.repository;

import com.example.demoactivity.domian.Activity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends PagingAndSortingRepository<Activity,String>{
    
}