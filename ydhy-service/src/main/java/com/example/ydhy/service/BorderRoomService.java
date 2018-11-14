package com.example.ydhy.service;


import com.example.ydhy.dto.BorderRoomInfo;
import com.example.ydhy.entity.BorderRoom;

import java.util.List;

public interface BorderRoomService {

    void update(BorderRoomInfo borderRoomInfo);

    BorderRoom getById(Integer id);

    void addBorderRoom(BorderRoom borderRoom);

    void delete(Integer id);

    List<BorderRoom> getBorderRoomByName(String borderName, Integer pageNo, Integer pageSize);

    List<BorderRoom> getBorderRoomByName(String roomName);
}
