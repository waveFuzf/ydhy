package com.example.ydhy.service;

import com.example.ydhy.dao.RequestMapper;
import com.example.ydhy.entity.Request;
import com.example.ydhy.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestMapper requestMapper;
    @Override
    public String examineAndSave(Request request) {
        List<Integer> requestList=requestMapper.conflictRequest(request.getBeginTime(),request.getEndTime());
        if (requestList.size()!=0){
            return "该时间段已有预约！";
        }
        JSONArray participantsInfo=new JSONArray();
        JSONObject participantInfo=new JSONObject();
        for (User user : request.getUserList()){
            participantInfo.put("id",user.getId());
            participantInfo.put("email",user.getEmail());
            participantsInfo.add(participantInfo);
        }
        request.setIsDelete("0");
        System.out.println(participantsInfo.toString());
        request.setParticipantsInfo(participantsInfo.toString());
        requestMapper.insert(request);
        return "成功预约,等待管理员同意!";
    }

    @Override
    public Integer changeState(Integer id) {
        Request request=new Request(id,"1");
        return requestMapper.updateByPrimaryKeySelective(request);
    }

    @Override
    public Request getModelById(Integer id) {
        Example example=new Example(Request.class);
        example.createCriteria().andEqualTo("id",id).andEqualTo("isDelete","0");
        return requestMapper.selectOneByExample(example);
    }

    @Override
    public Integer deleteApply(Integer id) {
        Request request=new Request();
        request.setId(id);
        request.setIsDelete("1");
        return requestMapper.updateByPrimaryKeySelective(request);
    }

    @Override
    public void changeState(Request request) {
        Example example=new Example(Request.class);
        example.createCriteria().andEqualTo("beginTime",request.getBeginTime())
                .andEqualTo("endTime",request.getEndTime()).andEqualTo("roomId",request.getRoomId())
                .andEqualTo("userId",request.getUserId());
        Request re=requestMapper.selectOneByExample(example);
        re.setState("1");
        requestMapper.updateByPrimaryKeySelective(re);
    }

    @Override
    public Request getRequest(Request request) {
        Example example=new Example(Request.class);
        example.createCriteria().andEqualTo("beginTime",request.getBeginTime())
                .andEqualTo("endTime",request.getEndTime()).andEqualTo("roomId",request.getRoomId())
                .andEqualTo("userId",request.getUserId()).andEqualTo("isDelete",0);
        Request re=requestMapper.selectOneByExample(example);
        return re;
    }

    @Override
    public List<Request> getRequestInfo(int id, int state) {
        Example example=new Example(Request.class);
        example.createCriteria().andEqualTo("isDelete",0)
                .andEqualTo("userId",id).andEqualTo("state",state);
        List<Request> re=requestMapper.selectByExample(example);
        return re;
    }

    @Override
    public List<Request> getUnDoneMeeting(int id, int i) {
        return requestMapper.getUnDoneMeeting(id,i);
    }
}
