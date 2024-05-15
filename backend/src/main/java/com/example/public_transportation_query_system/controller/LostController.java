package com.example.public_transportation_query_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.public_transportation_query_system.entity.po.Lost;
import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.DeleteVO;
import com.example.public_transportation_query_system.service.impl.LostServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "5-失物招领", description = "失物招领接口")
@RestController
@RequestMapping("/api/lost")
public class LostController {

    @Autowired
    LostServiceImpl lostServiceImpl;

    @Operation(summary = "获取失物招领信息", description = "获取失物招领信息接口")
    @GetMapping
    public Result<Object> get(Integer id) {
        return Result.success(lostServiceImpl.getById(id));
    }

    @Operation(summary = "添加失物招领", description = "添加失物招领接口")
    @PutMapping
    public Result<Object> put(@RequestBody Lost lost) {
        return lostServiceImpl.addLost(lost);
    }

    @Operation(summary = "修改失物招领", description = "修改失物招领接口")
    @PostMapping
    public Result<Object> post(@RequestBody Lost lost) {
        return lostServiceImpl.modifyLost(lost);
    }

    @Operation(summary = "删除失物招领", description = "删除失物招领接口")
    @DeleteMapping
    public Result<Object> delete(@RequestBody DeleteVO deleteVO) {
        return lostServiceImpl.deleteLost(deleteVO);
    }

    @Operation(summary = "分页查询失物招领列表", description = "分页查询失物招领列表接口")
    @GetMapping("/pageQuery")
    public Result<Object> pageQuery(BasePageQuery query) {
        return lostServiceImpl.getLostPage(query);
    }
}
