package com.example.ydhy.dao;

import com.example.ydhy.entity.Department;

import com.example.ydhy.tkMapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DepartmentMapper extends tkMapper<Department> {
    @Select({
            "select d.id,d.dept_name,d.introdecu,d.phone,u.email,d.director_id,d.dept_name," +
                    "u.real_name from department d LEFT JOIN user u ON d.director_id=u.id" +
                    " and d.is_delete !='1'"
    })
    @Results({
            @Result(column="real_name", property="directorName"),
    })
    List<Department> getDeptInfo();
}