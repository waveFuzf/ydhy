package com.example.ydhy.service;

import com.example.ydhy.dao.ScheduleMapper;
import com.example.ydhy.entity.Request;
import com.example.ydhy.entity.Schedule;
import com.example.ydhy.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Override
    public List<String> addSchedules(Request request) {
        List<String> users=new ArrayList<>();
        JSONObject jsonObject;
        JSONArray jsonArray= JSONArray.fromObject(request.getParticipantsInfo());
        Schedule schedule=new Schedule();
        schedule.setRequestId(request.getId());
        for(int i=0;i<jsonArray.size();i++){
            jsonObject=jsonArray.getJSONObject(i);
            schedule.setUserId(jsonObject.optInt("id"));
            if (scheduleMapper.insert(schedule)==1){
                users.add(jsonObject.optString("email"));
            }
        }
        return users;
    }

    @Override
    public List<String> deleteSchedule(Request request) {
        Schedule schedule=new Schedule();
        schedule.setIsDelete("1");
        List<String> users=new ArrayList<>();
        JSONObject jsonObject;
        JSONArray jsonArray= JSONArray.fromObject(request.getParticipantsInfo());
        Example example=new Example(Schedule.class);
        for (int i=0;i<jsonArray.size();i++){
            jsonObject=jsonArray.getJSONObject(i);
            example.createCriteria().andEqualTo("requestId",request.getId())
                    .andEqualTo("userId",jsonObject.optInt("id"));
            List<Schedule> schedule1=scheduleMapper.selectByExample(example);
            if (scheduleMapper.updateByExampleSelective(schedule,example)==1){
                users.add(jsonObject.optString("email"));
            }
        }
        return users;
    }

    @Override
    public List<Schedule> getScheduleByInfo(int id, Integer status) {
        return  scheduleMapper.getScheduleByInfo(id,status);
    }
}
