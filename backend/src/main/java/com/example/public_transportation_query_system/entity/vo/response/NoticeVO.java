package com.example.public_transportation_query_system.entity.vo.response;

import com.example.public_transportation_query_system.entity.po.Notice;

public class NoticeVO extends Notice {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
