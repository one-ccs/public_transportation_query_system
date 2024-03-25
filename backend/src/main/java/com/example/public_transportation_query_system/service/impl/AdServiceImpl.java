package com.example.public_transportation_query_system.service.impl;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.Ad;
import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.DeleteVO;
import com.example.public_transportation_query_system.mapper.AdMapper;
import com.example.public_transportation_query_system.service.IAdService;

@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements IAdService {

    public Result<Object> getPageAd(BasePageQuery query) {
        // 构造查询条件
        LambdaQueryWrapper<Ad> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(query.getStartDatetime() != null, Ad::getStartDatetime, query.getStartDatetime())
            .le(query.getEndDatetime() != null, Ad::getEndDatetime, query.getEndDatetime())
            .and(StringUtils.isNotBlank(query.getQuery()), i -> i
                .like(Ad::getTitle, query.getQuery())
                .or()
                .like(Ad::getDescribe, query.getQuery())
            );

        // 分页
        Page<Ad> page = new Page<>(
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

    public Result<Object> addAd(Ad ad) {
        // 表单验证
        if (StringUtils.isBlank(ad.getImgUrl())) {
            return Result.failure("广告图片不能为空");
        }
        if (ad.getStartDatetime() == null) {
            return Result.failure("开始日期不能为空");
        }
        if (ad.getEndDatetime() == null) {
            return Result.failure("结束日期不能为空");
        }

        // 清除 id
        ad.setId(null);

        // 添加广告
        if (this.save(ad)) {
            return Result.success("添加成功");
        }
        return Result.failure("添加失败");
    }

    public Result<Object> modifyAd(Ad ad) {
        // 表单验证
        if (ad.getId() == null) {
            return Result.failure("广告 id 不能为空");
        }
        if (StringUtils.isBlank(ad.getImgUrl())) {
            return Result.failure("广告图片不能为空");
        }
        if (ad.getStartDatetime() == null) {
            return Result.failure("开始日期不能为空");
        }
        if (ad.getEndDatetime() == null) {
            return Result.failure("结束日期不能为空");
        }

        // 修改广告
        if (this.updateById(ad)) {
            return Result.success("修改成功");
        }
        return Result.failure("修改失败");
    }

    public Result<Object> deleteAd(DeleteVO deleteVO) {
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
