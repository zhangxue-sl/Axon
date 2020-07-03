package com.example.demoactivity.domian;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;


import lombok.Data;

@Data
@Entity
//@Table(name = "place")
public class Place implements Serializable {
    static private final long serialVersionUID = -1L;
  
   // @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * 场地id
     */
    @Id
    private String id;
    /**
     * 场地名称
     */
    private String name;
    
}