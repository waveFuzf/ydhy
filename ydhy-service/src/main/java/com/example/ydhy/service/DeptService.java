package com.example.ydhy.service;

import com.example.ydhy.dto.DeptInfo;
import com.example.ydhy.entity.Department;

import java.util.List;

public interface DeptService {
    void updateDeptInfo(DeptInfo deptInfo);

    Department getDeptById(Integer id);

    void addDepartment(Department department);

    void delete(Integer id);

    List<Department> getDeptByName(String name, Integer pageNo, Integer pageSize);

    boolean getDeptByName(String s);

    List<Department> getAllDept(Integer pageNo);
}
