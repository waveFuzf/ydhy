package com.example.ydhy.service;

import com.example.ydhy.dao.BorderRoomMapper;
import com.example.ydhy.dto.BorderRoomInfo;
import com.example.ydhy.entity.BorderRoom;
import com.example.ydhy.entity.Department;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;
@Service
public class BorderRoomServiceImpl implements BorderRoomService {
    @Autowired
    private BorderRoomMapper borderRoomMapper;
    @Override
    public void update(BorderRoomInfo borderRoomInfo) {
        BorderRoom borderRoom = new BorderRoom(borderRoomInfo);
        borderRoomMapper.updateByPrimaryKeySelective(borderRoom);
    }

    @Override
    public BorderRoom getById(Integer id) {
        BorderRoom borderRoom=borderRoomMapper.selectByPrimaryKey(id);
        return borderRoom;
    }

    @Override
    public void addBorderRoom(BorderRoom borderRoom) {
        borderRoomMapper.insert(borderRoom);
    }

    @Override
    public void delete(Integer id) {
        BorderRoom borderRoom= new BorderRoom();
        borderRoom.setId(id);
        borderRoom.setIsDelete("1");
        borderRoomMapper.updateByPrimaryKeySelective(borderRoom);
    }

    @Override
    public List<BorderRoom> getBorderRoomByName(String borderName, Integer pageNo, Integer pageSize) {
        Page<BorderRoom> pageInfo = PageHelper.startPage(pageNo, pageSize);
        Example example=new Example(BorderRoom.class);
        example.createCriteria().andLike("roomName","%"+borderName+"%");
        List<BorderRoom> borderRooms=borderRoomMapper.selectByExample(example);
        return pageInfo;
    }

    @Override
    public List<BorderRoom> getBorderRoomByName(String roomName) {
        Example example = new Example(BorderRoom.class);
        example.createCriteria().andEqualTo("roomName",roomName).andEqualTo("isDelete","0");
        return borderRoomMapper.selectByExample(example);
    }
}
