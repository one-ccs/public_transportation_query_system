package com.example.public_transportation_query_system.entity.vo.request;

import java.util.List;

import lombok.Data;

@Data
public class DeleteVO {
    private Integer id = null;
    private List<Integer> ids = null;
}
