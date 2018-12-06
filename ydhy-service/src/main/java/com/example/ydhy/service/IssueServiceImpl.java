package com.example.ydhy.service;

import com.example.ydhy.dao.IssueMapper;
import com.example.ydhy.dto.IssueInfo;
import com.example.ydhy.entity.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class IssueServiceImpl implements IssueService{
    @Autowired
    private IssueMapper issueMapper;
    @Override
    public void save(Issue issue) {
        issue.setIsDelete("0");
        issueMapper.insert(issue);
    }

    @Override
    public void updateIssue(IssueInfo issueInfo, Integer userId) {
        Issue issue=new Issue();
        issue.setContent(issueInfo.getContent());
        Example example=new Example(Issue.class);
        example.createCriteria().andEqualTo("tag",issueInfo.getTag())
                .andEqualTo("userId",userId).andEqualTo("is_Delete","0")
                .andEqualTo("requestId",issueInfo.getRequestId());
        issueMapper.updateByExampleSelective(issue,example);
    }
}
