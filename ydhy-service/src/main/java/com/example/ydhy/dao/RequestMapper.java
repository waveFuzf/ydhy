package com.example.ydhy.dao;

import com.example.ydhy.entity.Request;

import java.util.Date;
import java.util.List;

import com.example.ydhy.tkMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RequestMapper extends tkMapper<Request> {
    @Select({
            "select r.id from request r where (r.begin_time <= #{endTime} and r.end_time >= #{endTime})" +
                    " OR (r.begin_time <= #{beginTime} and r.end_time >= #{beginTime}) OR " +
                    "(r.begin_time >= #{beginTime} and r.end_time <= #{endTime}) AND r.state='1'"
    })
    List<Integer> conflictRequest(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
