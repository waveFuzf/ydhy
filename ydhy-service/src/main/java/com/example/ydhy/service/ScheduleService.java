package com.example.ydhy.service;

import com.example.ydhy.entity.Request;
import com.example.ydhy.entity.User;

import java.util.List;

public interface ScheduleService {
    List<String> addSchedules(Request request);

    List<String> deleteSchedule(Request request);
}
