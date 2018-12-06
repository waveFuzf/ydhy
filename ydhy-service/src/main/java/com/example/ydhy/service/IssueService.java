package com.example.ydhy.service;

import com.example.ydhy.dto.IssueInfo;
import com.example.ydhy.entity.Issue;

public interface IssueService {
    void save(Issue issue);

    void updateIssue(IssueInfo issueInfo, Integer userId);
}
