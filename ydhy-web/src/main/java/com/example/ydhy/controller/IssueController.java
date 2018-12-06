package com.example.ydhy.controller;

import com.example.ydhy.Re.Result;
import com.example.ydhy.Re.ResultGenerator;
import com.example.ydhy.dto.IssueInfo;
import com.example.ydhy.entity.Issue;
import com.example.ydhy.service.IssueService;
import com.example.ydhy.service.RequestService;
import com.example.ydhy.util.TokenUtil;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Objects;

@CrossOrigin
@RestController
public class IssueController {
    @Autowired
    private IssueService issueService;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private RequestService requestService;

    @PostMapping("saveIssues")
    public Result saveIssues(@ApiParam("用户token")@RequestParam String token,
                               @ApiParam("议题信息")@RequestBody IssueInfo issueInfo) throws MessagingException {
        String str=tokenUtil.checkToken(token);
        if (str.equals("token无效")){
            return ResultGenerator.genFailResult(str);
        }
        JSONObject jsonObject=JSONObject.fromObject(str);
        Issue issue=new Issue(issueInfo);
        issue.setUserId(jsonObject.optInt("id"));
        issueService.save(issue);
        return ResultGenerator.genSuccessResult();
    }
    @PostMapping("deleteIssues")
    public Result deleteIssues(@ApiParam("用户token")@RequestParam String token,
                             @ApiParam("议题信息")@RequestBody Issue issueInfo) throws MessagingException {
        String str=tokenUtil.checkToken(token);
        if (str.equals("token无效")){
            return ResultGenerator.genFailResult(str);
        }
        JSONObject jsonObject=JSONObject.fromObject(str);
        Integer userId=jsonObject.optInt("id");
        if (!Objects.equals(userId,
                requestService.getModelById(issueInfo.getRequestId()).getUserId())){
            return ResultGenerator.genFailResult("需要本人操作。");
        }
        issueService.delete(issueInfo);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("updateIssues")
    public Result updateIssues(@ApiParam("用户token")@RequestParam String token,
                             @ApiParam("议题信息")@RequestBody Issue issue) throws MessagingException {
        String str=tokenUtil.checkToken(token);
        if (str.equals("token无效")){
            return ResultGenerator.genFailResult(str);
        }
        JSONObject jsonObject=JSONObject.fromObject(str);
        Integer userId=jsonObject.optInt("id");
        if (!Objects.equals(userId,
                requestService.getModelById(issue.getRequestId()).getUserId())){
            return ResultGenerator.genFailResult("需要本人操作。");
        }
        issueService.updateIssue(issue,userId);
        return null;
    }

}
