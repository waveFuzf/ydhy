package com.example.ydhy.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;

@Data
public class Schedule {
    @Id
    private Integer id;

    private Integer userId;

    private Integer requestId;

    private String isParticipate;

    private String isDelete;

    @Transient
    private Request request;
}