package com.example.public_transportation_query_system.entity.vo.request;

import com.example.public_transportation_query_system.entity.vo.BaseQuery;

public class QueryNoticeVO extends BaseQuery {
    private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
