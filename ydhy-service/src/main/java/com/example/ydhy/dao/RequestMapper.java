package com.example.ydhy.dao;

import com.example.ydhy.entity.Request;

import java.util.Date;
import java.util.List;

import com.example.ydhy.tkMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface RequestMapper extends tkMapper<Request> {
    @Select({
            "select r.id from request r where (r.begin_time <= #{endTime} and r.end_time >= #{endTime})" +
                    " OR (r.begin_time <= #{beginTime} and r.end_time >= #{beginTime}) OR " +
                    "(r.begin_time >= #{beginTime} and r.end_time <= #{endTime}) AND r.state='1'"
    })
    List<Integer> conflictRequest(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @Select({
            "select * from request where id = #{id} and is_delete = 0 "
    })
    Request getRequestByRequestId(Integer id);

    @Select({
            "select re.*,u.real_name,r.room_name from request re left join schedule s ON s.request_id = re.id " +
                    "left join user u ON u.id=re.user_id " +
                    "left join border_room r ON r.id=re.room_id where s.user_id = #{id} and s.is_delete = 0 " +
                    "and s.status = #{status} and re.is_delete = 0 and re.state = 1 ORDER BY re.begin_time DESC "
    })
    @Results({
            @Result(column = "real_name",property = "userName"),
            @Result(column = "room_name",property = "roomName")
    }
    )
    List<Request> getUnDoneMeeting(@Param("id") int id,@Param("status") int i);
}
