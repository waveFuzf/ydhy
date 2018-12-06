package com.example.ydhy.service;

import com.example.ydhy.dao.IssueMapper;
import com.example.ydhy.entity.Issue;
import com.example.ydhy.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
    public void updateIssue(Issue issueInfo, Integer userId) {
        Issue issue=new Issue();
        issue.setContent(issueInfo.getContent());
        if (issueInfo.getUserList()!=null){
            JSONArray jsonArray=new JSONArray();
            JSONObject jsonObject=new JSONObject();
            for (User user:issueInfo.getUserList()){
                jsonObject.put("id",user.getId());
                jsonObject.put("name",user.getRealName());
                jsonArray.add(jsonObject);
            }
            issue.setResoveUserInfo(jsonArray.toString());
        }
        Example example=new Example(Issue.class);
        example.createCriteria().andEqualTo("tag",issueInfo.getTag())
                .andEqualTo("userId",userId).andEqualTo("is_Delete","0")
                .andEqualTo("requestId",issueInfo.getRequestId());
        issueMapper.updateByExampleSelective(issue,example); 
    }

    @Override
    public void delete(Issue issue) {
        issue.setIsDelete("1");
        issueMapper.updateByPrimaryKeySelective(issue);
    }
}
