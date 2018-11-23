package com.example.ydhy.service;

import com.example.ydhy.dao.DepartmentMapper;
import com.example.ydhy.dto.DeptInfo;
import com.example.ydhy.entity.Department;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public void updateDeptInfo(DeptInfo deptInfo) {
        Department dept=new Department(deptInfo);
        dept.setUpdateTime(new Date());
        departmentMapper.updateByPrimaryKeySelective(dept);
    }

    @Override
    public Department getDeptById(Integer id) {
        Example example=new Example(Department.class);
        example.createCriteria().andEqualTo("id",id).andEqualTo("isDelete","0");
        List<Department> departments=departmentMapper.selectByExample(example);
        if (departments.size()==0)
            return null;
        return departments.get(0);
    }

    @Override
    public void addDepartment(Department department) {
        departmentMapper.insert(department);
    }

    @Override
    public void delete(Integer id) {
        Department department=new Department();
        department.setId(id);
        department.setIsDelete("1");
        departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public List<Department> getDeptByName(String deptName, Integer pageNo, Integer pageSize) {
        Page<Department> pageInfo = PageHelper.startPage(pageNo, pageSize);
        Example example=new Example(Department.class);
        example.createCriteria().andLike("deptName","%"+deptName+"%")
                .andEqualTo("isDelete","0");
        List<Department> departments=departmentMapper.selectByExample(example);
        return pageInfo;
    }

    @Override
    public boolean getDeptByName(String deptName) {
        Example example=new Example(Department.class);
        example.createCriteria().andEqualTo("deptName",
                deptName).andEqualTo("isDelete","0");
        return departmentMapper.selectByExample(example).size()>0?false:true;
    }

    @Override
    public List<Department> getAllDept() {
        Example example=new Example(Department.class);
        example.createCriteria().andEqualTo("isDelete","0");
        return departmentMapper.selectByExample(example);
    }
}
