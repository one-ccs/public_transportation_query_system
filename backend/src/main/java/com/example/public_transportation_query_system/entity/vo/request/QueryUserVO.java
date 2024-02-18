package com.example.public_transportation_query_system.entity.vo.request;

import com.example.public_transportation_query_system.entity.vo.BaseQuery;

public class QueryUserVO extends BaseQuery {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
