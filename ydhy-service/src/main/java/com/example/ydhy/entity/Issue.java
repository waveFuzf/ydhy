package com.example.ydhy.entity;

import com.example.ydhy.dto.IssueInfo;
import lombok.Data;

import javax.persistence.Id;
@Data
public class Issue {
    @Id
    private Integer id;

    private Integer userId;

    private Integer requestId;

    private String content;

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