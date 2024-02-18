package com.example.public_transportation_query_system.entity.vo.request;

import com.example.public_transportation_query_system.entity.vo.BaseQuery;

public class QueryUserVO extends BaseQuery {
    private String username;

    private Integer filterFlag = 0;

    public Integer getFilterFlag() {
        return filterFlag;
    }

    public void setFilterFlag(Integer filterFlag) {
        this.filterFlag = filterFlag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
