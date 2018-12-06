package com.example.ydhy.entity;

import lombok.Data;

import javax.persistence.Id;
@Data
public class Schedule {
    @Id
    private Integer id;

    private Integer userId;

    private Integer requestId;

    private String isParticipate;

    private String isDelete;

}