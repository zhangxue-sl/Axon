package com.example.demoactivity.repository;

import com.example.demoactivity.domian.Place;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends PagingAndSortingRepository<Place,String>{
    
}