package com.example.ydhy.controller;

import com.example.ydhy.Re.Result;
import com.example.ydhy.Re.ResultGenerator;
import com.example.ydhy.aop.UserTokenAop;
import com.example.ydhy.dao.RequestMapper;
import com.example.ydhy.dto.IssueInfo;
import com.example.ydhy.entity.Issue;
import com.example.ydhy.entity.Request;
import com.example.ydhy.service.IssueService;
import com.example.ydhy.service.RequestService;
import com.example.ydhy.service.ScheduleService;
import com.example.ydhy.util.EmailUtil;
import com.example.ydhy.util.TokenUtil;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Objects;
@CrossOrigin
@RestController
public class RequestController {
    @Autowired
    private RequestService requestService;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private RequestMapper requestMapper;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private IssueService issueService;

    @PostMapping("applyForRoom")
    public Result applyForRoom(@ApiParam("用户token")@RequestParam String token,
                               @ApiParam("申请信息")@RequestBody Request request) throws MessagingException {
        String str=tokenUtil.checkToken(token);
        if (str.equals("token无效")){
            return ResultGenerator.genFailResult(str);
        }
        JSONObject jsonObject=JSONObject.fromObject(str);
        request.setUserId(Integer.valueOf(jsonObject.optString("id")));
        request.setState("0");
        boolean flag=Objects.equals(jsonObject.optString("position"),"主管");
        if (flag){
            request.setState("1");
        }
        String resultStr= requestService.examineAndSave(request);
        if (resultStr.equals("该时间段已有预约！")){
            return ResultGenerator.genSuccessResult(resultStr);
        }
        Request re=requestService.getRequest(request);
        Issue issue=new Issue();
        issue.setRequestId(re.getId());
        issue.setUserId(Integer.valueOf(jsonObject.optString("id")));
        for (IssueInfo obj:request.getIssueList()){
            issue.setContent(obj.getContent());
            issue.setTag(obj.getTag());
            issueService.save(issue);
        }
        if (flag){
            List<String> users=scheduleService.addSchedules(re);
            String[] s= users.toArray(new String[users.size()]);
            if (s.length>0){
                emailUtil.sendEmails(s,re,true);
            }
            return ResultGenerator.genSuccessResult("成功预约！");
        }

        return ResultGenerator.genSuccessResult(resultStr);
    }

    @PostMapping("agreeApply")
    public Result agreeApply(@ApiParam("用户token")@RequestParam String token,
                               @ApiParam(value = "申请信息ID",example = "1")@RequestParam Integer id){
        Integer returnCode=null;
        String str=tokenUtil.checkToken(token);
        if (str.equals("token无效")){
            return ResultGenerator.genFailResult(str);
        }
        JSONObject jsonObject=JSONObject.fromObject(str);
        if (jsonObject.optString("isSuper").equals("0")) {
            return ResultGenerator.genFailResult("权限不足");
        }
        try{
            returnCode=requestService.changeState(id);
            Request re=requestService.getModelById(id);
            List<String> users=scheduleService.addSchedules(re);
            String[] s= users.toArray(new String[users.size()]);
            if (s.length>0) {
                emailUtil.sendEmails(s, re, true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(returnCode);
    }

    @PostMapping("revokeApply")
    public Result deleteModel(@ApiParam("用户token")@RequestParam String token,
                              @ApiParam(value = "删除信息ID",example = "1")@RequestParam Integer id) throws MessagingException {
        Request request=requestService.getModelById(id);
        if (request==null){
            return ResultGenerator.genFailResult("该申请已删除或未存在。");
        }
        String str=tokenUtil.checkToken(token);
        JSONObject jsonObject=JSONObject.fromObject(str);
        if (str.equals("token无效")){
            return ResultGenerator.genFailResult(str);
        }
        if (!(jsonObject.optString("isSuper").equals("1")||
                Objects.equals(jsonObject.optInt("id"),request.getUserId()))){
            return ResultGenerator.genFailResult("权限不足。需要本人或者管理员操作。");
        }
        if (Objects.equals(request.getState(),"1")){
            List<String> users=scheduleService.deleteSchedule(request);
            String[] s= users.toArray(new String[users.size()]);
            if (s.length>0) {
                emailUtil.sendEmails(s, request, false);
            }
        }
        return ResultGenerator.genSuccessResult(requestService.deleteApply(id));
    }

    @PostMapping("test")
    public Result test(@ApiParam("申请信息")@RequestBody Request request){
        return ResultGenerator.genSuccessResult(requestMapper.conflictRequest(request.getBeginTime(),request.getEndTime()));
    }
    @PostMapping("aoptest")
    @UserTokenAop
    public Result aoptest(@ApiParam("申请信息")@RequestParam String token){
        return ResultGenerator.genSuccessResult("我咋个知道");
    }

}
