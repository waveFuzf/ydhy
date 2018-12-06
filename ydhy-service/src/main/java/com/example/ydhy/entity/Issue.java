package com.example.ydhy.entity;

import com.example.ydhy.dto.IssueInfo;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Data
public class Issue {
    @Id
    private Integer id;

    private Integer userId;

    private Integer requestId;

    private String type;

    private String content;

    private String resoveUserInfo;

    @Transient
    private List<User> userList;

    private String resolve;

    private Integer tag;

    private String isDelete;

    public Issue(){

}

    public Issue(IssueInfo issueInfo) {
        this.requestId=issueInfo.getRequestId();
        this.content=issueInfo.getContent();
        this.tag=issueInfo.getTag();
    }
}