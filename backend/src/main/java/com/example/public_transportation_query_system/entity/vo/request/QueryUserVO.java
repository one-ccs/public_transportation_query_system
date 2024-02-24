package com.example.public_transportation_query_system.entity.vo.request;

import com.example.public_transportation_query_system.entity.vo.BaseQuery;

public class QueryUserVO extends BaseQuery {
    private String query;

    private Integer filterFlag = 0;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getFilterFlag() {
        return filterFlag;
    }

    public void setFilterFlag(Integer filterFlag) {
        this.filterFlag = filterFlag;
    }
}
