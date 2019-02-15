package com.example.ydhy.dao;

import com.example.ydhy.entity.Schedule;

import java.util.List;

import com.example.ydhy.tkMapper;
import org.apache.ibatis.annotations.*;

public interface ScheduleMapper extends tkMapper<Schedule> {
    @Select({
            "select * from schedule where user_id = #{id} and status = #{status} and is_delete = 0 "
    })
    @Results({
            @Result(column = "request_id",property = "request",one = @One(select = "com.example.ydhy.dao.RequestMapper.getRequestByRequestId"))
    })
    List<Schedule> getScheduleByInfo(@Param("id") int id,@Param("status") Integer status);
}