package com.example.public_transportation_query_system.entity.vo.request;

import com.example.public_transportation_query_system.entity.vo.BasePageQuery;

public class QueryLostVO extends BasePageQuery {
    private Byte status;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
