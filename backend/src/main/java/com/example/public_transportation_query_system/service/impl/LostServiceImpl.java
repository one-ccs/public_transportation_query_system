package com.example.public_transportation_query_system.service.impl;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.Lost;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.DeleteVO;
import com.example.public_transportation_query_system.entity.vo.request.QueryLostVO;
import com.example.public_transportation_query_system.mapper.LostMapper;
import com.example.public_transportation_query_system.service.ILostService;

@Service
public class LostServiceImpl extends ServiceImpl<LostMapper, Lost> implements ILostService {

    public Result<Object> getLostPage(QueryLostVO query) {
        // 构造查询条件
        LambdaQueryWrapper<Lost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(query.getStartDatetime() != null, Lost::getPickDatetime, query.getStartDatetime())
            .le(query.getEndDatetime() != null, Lost::getPickDatetime, query.getEndDatetime())
            .and(StringUtils.isNotBlank(query.getQuery()), i -> i
                .like(Lost::getDescribe, query.getQuery())
                .or()
                .like(Lost::getAddress, query.getQuery())
            )
            .and(query.getStatus() != null, i -> i
                .eq(Lost::getStatus, query.getStatus())
            );

        // 分页
        Page<Lost> page = new Page<>(
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

    public Result<Object> addLost(Lost lost) {
        // 表单验证
        if (StringUtils.isBlank(lost.getDescribe())) {
            return Result.failure("失物描述不能为空");
        }
        if (StringUtils.isBlank(lost.getAddress())) {
            return Result.failure("拾取地点不能为空");
        }
        if (lost.getPickDatetime() == null) {
            return Result.failure("拾取时间不能为空");
        }

        // 清除 id
        lost.setId(null);

        // 添加公告
        if (this.save(lost)) {
            return Result.success("添加成功");
        }
        return Result.failure("添加失败");
    }

    public Result<Object> modifyLost(Lost lost) {
        // 表单验证
        if (lost.getId() == null) {
            return Result.failure("失物招领 id 不能为空");
        }
        if (StringUtils.isBlank(lost.getDescribe())) {
            return Result.failure("失物描述不能为空");
        }
        if (StringUtils.isBlank(lost.getAddress())) {
            return Result.failure("拾取地点不能为空");
        }
        if (lost.getPickDatetime() == null) {
            return Result.failure("拾取时间不能为空");
        }

        // 修改公告
        if (this.updateById(lost)) {
            return Result.success("修改成功");
        }
        return Result.failure("修改失败");
    }

    public Result<Object> deleteLost(DeleteVO deleteVO) {
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
