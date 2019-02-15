package com.example.ydhy.entity;

import com.example.ydhy.dto.IssueInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
public class Request {
    @Id
    private Integer id;
    @Column(name="user_id")
    private Integer userId;
    @Transient
    private String userName;
    @Column(name="room_id")
    private Integer roomId;
    @Transient
    private String roomName;
    @Column(name="begin_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date beginTime;
    @Column(name="end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;
    @Column(name="state")
    private String state;
    @Column(name="scale")
    private String scale;
    @Column(name="is_delete")
    private String isDelete;
    @Column(name="participants_info")
    private String participantsInfo;
    @Column(name="theme")
    private String theme;
    @Column(name="introduce")
    private String introduce;

    @Transient
    private List<User> userList;

    @Transient
    private List<IssueInfo> issueList;

    public Request(Integer id, String state) {
        this.id=id;
        this.state=state;
    }

    public Request(){

    }

}