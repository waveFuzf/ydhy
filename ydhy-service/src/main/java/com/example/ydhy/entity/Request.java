package com.example.ydhy.entity;

import com.example.ydhy.dto.IssueInfo;
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
    @Column(name="room_id")
    private Integer roomId;
    @Column(name="begin_time")
    private Date beginTime;
    @Column(name="end_time")
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