package com.example.demoactivity.domian;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Activity implements Serializable{
   
    static private final long serialVersionUID = -1L;
    /**
     * 活动id
     */
    @Id
    private String id;

     private String name;
    /**
     * 开始事件
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date startDate;
    /**
     * 活动内容
     */
    private String content;
    /**
     * 活动人数
     */
    private Integer count;

    /**
     * 场地id
     */
    
    private String palceId;
}