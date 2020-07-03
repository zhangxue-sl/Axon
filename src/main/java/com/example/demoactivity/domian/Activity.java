package com.example.demoactivity.domian;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;


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
    /**
     * 开始事件
     */
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