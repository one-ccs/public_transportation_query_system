package com.example.public_transportation_query_system.entity.vo;

import lombok.Data;

@Data
public class BaseQuery {
    private String query;

    @Override
    public String toString() {
        return "BaseQuery{" +
            "query = " + query +
        "}";
    }
}
