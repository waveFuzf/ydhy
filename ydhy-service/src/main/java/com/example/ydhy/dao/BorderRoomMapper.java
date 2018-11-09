package com.example.ydhy.dao;

import com.example.ydhy.entity.BorderRoom;
import com.example.ydhy.entity.BorderRoomExample;
import java.util.List;

import com.example.ydhy.tkMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface BorderRoomMapper extends tkMapper<BorderRoom> {
//    @SelectProvider(type=BorderRoomSqlProvider.class, method="countByExample")
//    int countByExample(BorderRoomExample example);
//
//    @DeleteProvider(type=BorderRoomSqlProvider.class, method="deleteByExample")
//    int deleteByExample(BorderRoomExample example);
//
//    @Delete({
//        "delete from border_room",
//        "where id = #{id,jdbcType=INTEGER}"
//    })
//    int deleteByPrimaryKey(Integer id);
//
//    @Insert({
//        "insert into border_room (id, room_name, ",
//        "position, status, ",
//        "introduce, create_time, ",
//        "update_time)",
//        "values (#{id,jdbcType=INTEGER}, #{roomName,jdbcType=VARCHAR}, ",
//        "#{position,jdbcType=VARCHAR}, #{status,jdbcType=VARCHAR}, ",
//        "#{introduce,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
//        "#{updateTime,jdbcType=TIMESTAMP})"
//    })
//    int insert(BorderRoom record);
//
//    @InsertProvider(type=BorderRoomSqlProvider.class, method="insertSelective")
//    int insertSelective(BorderRoom record);
//
//    @SelectProvider(type=BorderRoomSqlProvider.class, method="selectByExample")
//    @Results({
//        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
//        @Result(column="room_name", property="roomName", jdbcType=JdbcType.VARCHAR),
//        @Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
//        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
//        @Result(column="introduce", property="introduce", jdbcType=JdbcType.VARCHAR),
//        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
//        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
//    })
//    List<BorderRoom> selectByExample(BorderRoomExample example);
//
//    @Select({
//        "select",
//        "id, room_name, position, status, introduce, create_time, update_time",
//        "from border_room",
//        "where id = #{id,jdbcType=INTEGER}"
//    })
//    @Results({
//        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
//        @Result(column="room_name", property="roomName", jdbcType=JdbcType.VARCHAR),
//        @Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
//        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
//        @Result(column="introduce", property="introduce", jdbcType=JdbcType.VARCHAR),
//        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
//        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
//    })
//    BorderRoom selectByPrimaryKey(Integer id);
//
//    @UpdateProvider(type=BorderRoomSqlProvider.class, method="updateByExampleSelective")
//    int updateByExampleSelective(@Param("record") BorderRoom record, @Param("example") BorderRoomExample example);
//
//    @UpdateProvider(type=BorderRoomSqlProvider.class, method="updateByExample")
//    int updateByExample(@Param("record") BorderRoom record, @Param("example") BorderRoomExample example);
//
//    @UpdateProvider(type=BorderRoomSqlProvider.class, method="updateByPrimaryKeySelective")
//    int updateByPrimaryKeySelective(BorderRoom record);
//
//    @Update({
//        "update border_room",
//        "set room_name = #{roomName,jdbcType=VARCHAR},",
//          "position = #{position,jdbcType=VARCHAR},",
//          "status = #{status,jdbcType=VARCHAR},",
//          "introduce = #{introduce,jdbcType=VARCHAR},",
//          "create_time = #{createTime,jdbcType=TIMESTAMP},",
//          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
//        "where id = #{id,jdbcType=INTEGER}"
//    })
//    int updateByPrimaryKey(BorderRoom record);
}