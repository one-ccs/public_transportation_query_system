package com.example.public_transportation_query_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.public_transportation_query_system.entity.po.Role;
import com.example.public_transportation_query_system.entity.vo.BaseQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.service.impl.RoleServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "2-角色", description = "角色接口")
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    RoleServiceImpl roleServiceImpl;

    @Operation(summary = "获取角色列表", description = "获取角色列表接口")
    @GetMapping
    public Result<Object> apiRoleGet(BaseQuery query) {
        return roleServiceImpl.getRoles(query);
    }

    @Operation(summary = "添加角色", description = "添加角色接口")
    @PutMapping
    public Result<Object> apiRolePut(@RequestBody Role role) {
        return roleServiceImpl.addRole(role);
    }

    @Operation(summary = "修改角色", description = "修改角色接口")
    @PostMapping
    public Result<Object> apiRolePost(@RequestBody Role role) {
        return roleServiceImpl.modifyRole(role);
    }

    @Operation(summary = "删除角色", description = "删除角色接口")
    @DeleteMapping
    public Result<Object> apiRoleDelete(@RequestBody Role role) {
        return roleServiceImpl.deleteRole(role.getId());
    }
}
