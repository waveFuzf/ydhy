package com.example.ydhy.entity;

import com.example.ydhy.dto.BorderRoomInfo;
import lombok.Data;
import lombok.Generated;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Data
public class BorderRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roomName;

    private String position;

    private String status;

    private String introduce;

    private Date createTime;

    private Date updateTime;

    private String isDelete;

    private Integer directorId;

    public BorderRoom(BorderRoomInfo borderRoomInfo) {
        this.id=borderRoomInfo.getId();
        this.introduce=borderRoomInfo.getIntroduce();
        this.position=borderRoomInfo.getPosition();
        this.roomName=borderRoomInfo.getRoomName();
    }

    public BorderRoom(){

    }

}