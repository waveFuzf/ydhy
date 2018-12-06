package com.example.ydhy.service;

import com.example.ydhy.entity.Issue;

public interface IssueService {
    void save(Issue issue);

    void updateIssue(Issue issueInfo, Integer userId);

    void delete(Issue issue);
}
