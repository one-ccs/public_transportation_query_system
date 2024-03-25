package com.example.public_transportation_query_system.service.impl;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.Route;
import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.DeleteVO;
import com.example.public_transportation_query_system.mapper.RouteMapper;
import com.example.public_transportation_query_system.service.IRouteService;

@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements IRouteService {

    public Result<Object> getPageRoute(BasePageQuery query) {
        // 构造查询条件
        LambdaQueryWrapper<Route> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(query.getStartDatetime() != null, Route::getFirstTime, query.getStartDatetime())
            .le(query.getEndDatetime() != null, Route::getLastTime, query.getEndDatetime())
            .like(StringUtils.isNotBlank(query.getQuery()), Route::getNo, query.getQuery());

        // 分页
        Page<Route> page = new Page<>(
            Optional.ofNullable(query.getPageIndex()).orElse(1),
            Optional.ofNullable(query.getPageSize()).orElse(10)
        );

        // 获取分页数据
        this.page(page, queryWrapper);

        // 构造返回结构
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("list", page.getRecords());

        return Result.success("查询成功", map);
    }

    public Result<Object> addRoute(Route route) {
        // 表单验证
        if (StringUtils.isBlank(route.getNo())) {
            return Result.failure("线路号不能为空");
        }

        // 清除 id
        route.setId(null);

        // 添加线路
        if (this.save(route)) {
            return Result.success("添加成功");
        }
        return Result.failure("添加失败");
    }

    public Result<Object> modifyRoute(Route route) {
        // 表单验证
        if (route.getId() == null) {
            return Result.failure("线路 id 不能为空");
        }

        // 修改线路
        if (this.updateById(route)) {
            return Result.success("修改成功");
        }
        return Result.failure("修改失败");
    }

    public Result<Object> deleteRoute(DeleteVO deleteVO) {
        if (deleteVO.getIds() != null) {
            // 批量删除
            if (this.removeBatchByIds(deleteVO.getIds())) {
                return Result.success("批量删除成功");
            }
            return Result.failure("批量删除失败");
        }
        else if (deleteVO.getId() != null) {
            // 单个删除
            if (this.removeById(deleteVO.getId())) {
                return Result.success("删除成功");
            }
            return Result.failure("删除失败");
        }
        return Result.failure("删除失败，参数 id 和 ids 不能同时为空");
    }

}
