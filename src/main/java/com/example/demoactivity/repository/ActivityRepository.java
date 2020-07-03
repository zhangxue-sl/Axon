package com.example.demoactivity.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demoactivity.domian.Activity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends PagingAndSortingRepository<Activity, String> {
    // @Query("SELECT * FROM ACTIVITY WHERE NAME=: name")
    // List<Activity> findByName(String name);

    @Query("SELECT a FROM Activity a  WHERE a.name=:name")
    List<Activity> findByName(String name);
 
    //根据时间查找人数
   // @Query("SELECT COUNT(a.startDate) FROM Activity a GROUP BY a.time ")

   @Query("select d from Activity d where d.startDate >= :from and d.startDate <= :to")
    List<Activity> findByStartDateBetween(@Param("from") Date from,@Param("to") Date to);

   @Query("select SUM(d.count) from Activity d where d.startDate >= :from and d.startDate <= :to")
   Long countByStartDateBetween(@Param("from") Date from,@Param("to") Date to);

//   @Query("SELECT a FROM Activity a  WHERE a.startDate=:startDate")
//   List<Activity> findActivityByTime(String startDate);  

    
    // @Query("select * from role wehre role.name=:name")
    // List<Activity> findByName(String name);
}