package com.example.ydhy.dao;

import com.example.ydhy.entity.Schedule;
import com.example.ydhy.entity.ScheduleExample;
import java.util.List;

import com.example.ydhy.tkMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface ScheduleMapper extends tkMapper<Schedule> {
    @Select({
            "select * from schedule where user_id = #{id} and status = #{status} "
    })
    @Results({
            @Result(column = "request_id",property = "request",one = @One(select = "com.example.ydhy.dao.RequestMapper.getRequestByRequestId"))
    })
    List<Schedule> getScheduleByInfo(@Param("id") int id,@Param("status") Integer status);
}