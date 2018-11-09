package com.example.ydhy.dao;

import com.example.ydhy.entity.User;
import com.example.ydhy.entity.UserExample;
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

public interface UserMapper extends tkMapper<User> {
//    @SelectProvider(type=UserSqlProvider.class, method="countByExample")
//    int countByExample(UserExample example);
//
//    @DeleteProvider(type=UserSqlProvider.class, method="deleteByExample")
//    int deleteByExample(UserExample example);
//
//    @Delete({
//        "delete from user",
//        "where id = #{id,jdbcType=INTEGER}"
//    })
//    int deleteByPrimaryKey(Integer id);
//
//    @Insert({
//        "insert into user (id, login_name, ",
//        "real_name, phone, ",
//        "email, dept_id, password, ",
//        "is_delete, is_super, ",
//        "role, status, create_time, ",
//        "update_time, vchat_num)",
//        "values (#{id,jdbcType=INTEGER}, #{loginName,jdbcType=VARCHAR}, ",
//        "#{realName,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
//        "#{email,jdbcType=VARCHAR}, #{deptId,jdbcType=INTEGER}, #{password,jdbcType=VARCHAR}, ",
//        "#{isDelete,jdbcType=VARCHAR}, #{isSuper,jdbcType=VARCHAR}, ",
//        "#{role,jdbcType=VARCHAR}, #{status,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
//        "#{updateTime,jdbcType=TIMESTAMP}, #{vchatNum,jdbcType=VARCHAR})"
//    })
//    int insert(User record);
//
//    @InsertProvider(type=UserSqlProvider.class, method="insertSelective")
//    int insertSelective(User record);
//
//    @SelectProvider(type=UserSqlProvider.class, method="selectByExample")
//    @Results({
//        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
//        @Result(column="login_name", property="loginName", jdbcType=JdbcType.VARCHAR),
//        @Result(column="real_name", property="realName", jdbcType=JdbcType.VARCHAR),
//        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
//        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
//        @Result(column="dept_id", property="deptId", jdbcType=JdbcType.INTEGER),
//        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
//        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.VARCHAR),
//        @Result(column="is_super", property="isSuper", jdbcType=JdbcType.VARCHAR),
//        @Result(column="role", property="role", jdbcType=JdbcType.VARCHAR),
//        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
//        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
//        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
//        @Result(column="vchat_num", property="vchatNum", jdbcType=JdbcType.VARCHAR)
//    })
//    List<User> selectByExample(UserExample example);
//
//    @Select({
//        "select",
//        "id, login_name, real_name, phone, email, dept_id, password, is_delete, is_super, ",
//        "role, status, create_time, update_time, vchat_num",
//        "from user",
//        "where id = #{id,jdbcType=INTEGER}"
//    })
//    @Results({
//        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
//        @Result(column="login_name", property="loginName", jdbcType=JdbcType.VARCHAR),
//        @Result(column="real_name", property="realName", jdbcType=JdbcType.VARCHAR),
//        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
//        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
//        @Result(column="dept_id", property="deptId", jdbcType=JdbcType.INTEGER),
//        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
//        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.VARCHAR),
//        @Result(column="is_super", property="isSuper", jdbcType=JdbcType.VARCHAR),
//        @Result(column="role", property="role", jdbcType=JdbcType.VARCHAR),
//        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
//        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
//        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
//        @Result(column="vchat_num", property="vchatNum", jdbcType=JdbcType.VARCHAR)
//    })
//    User selectByPrimaryKey(Integer id);
//
//    @UpdateProvider(type=UserSqlProvider.class, method="updateByExampleSelective")
//    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);
//
//    @UpdateProvider(type=UserSqlProvider.class, method="updateByExample")
//    int updateByExample(@Param("record") User record, @Param("example") UserExample example);
//
//    @UpdateProvider(type=UserSqlProvider.class, method="updateByPrimaryKeySelective")
//    int updateByPrimaryKeySelective(User record);
//
//    @Update({
//        "update user",
//        "set login_name = #{loginName,jdbcType=VARCHAR},",
//          "real_name = #{realName,jdbcType=VARCHAR},",
//          "phone = #{phone,jdbcType=VARCHAR},",
//          "email = #{email,jdbcType=VARCHAR},",
//          "dept_id = #{deptId,jdbcType=INTEGER},",
//          "password = #{password,jdbcType=VARCHAR},",
//          "is_delete = #{isDelete,jdbcType=VARCHAR},",
//          "is_super = #{isSuper,jdbcType=VARCHAR},",
//          "role = #{role,jdbcType=VARCHAR},",
//          "status = #{status,jdbcType=VARCHAR},",
//          "create_time = #{createTime,jdbcType=TIMESTAMP},",
//          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
//          "vchat_num = #{vchatNum,jdbcType=VARCHAR}",
//        "where id = #{id,jdbcType=INTEGER}"
//    })
//    int updateByPrimaryKey(User record);
}