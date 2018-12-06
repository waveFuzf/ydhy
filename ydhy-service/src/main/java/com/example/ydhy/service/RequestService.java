package com.example.ydhy.service;

import com.example.ydhy.entity.Request;

public interface RequestService {
    String examineAndSave(Request request);

    Integer changeState(Integer id);

    Request getModelById(Integer id);

    Integer deleteApply(Integer id);

    void changeState(Request request);

    Request getRequest(Request request);
}
