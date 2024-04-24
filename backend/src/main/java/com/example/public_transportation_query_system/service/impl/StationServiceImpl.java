package com.example.public_transportation_query_system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.bo.StationBO;
import com.example.public_transportation_query_system.entity.po.Station;
import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.DeleteVO;
import com.example.public_transportation_query_system.mapper.StationMapper;
import com.example.public_transportation_query_system.service.IStationService;

@Service
public class StationServiceImpl extends ServiceImpl<StationMapper, Station> implements IStationService {

    @Autowired
    StationMapper stationMapper;

    /**
     * 返回 routeId 对应的所有 站点
     * @param routeId 线路 id
     * @return
     */
    public List<StationBO> getStationsByRouteId(Integer routeId) {
        return stationMapper.getStationsByRouteId(routeId);
    }

    public Result<Object> getStationPage(BasePageQuery query) {
        // 构造查询条件
        LambdaQueryWrapper<Station> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
            .ge(query.getStartDatetime() != null, Station::getOpeningDatetime, query.getStartDatetime())
            .le(query.getEndDatetime() != null, Station::getOpeningDatetime, query.getEndDatetime())
            .like(StringUtils.isNotBlank(query.getQuery()), Station::getSitename, query.getQuery());

        // 分页
        Page<Station> page = new Page<>(
            Optional.ofNullable(query.getPageIndex()).orElse(1),
            Optional.ofNullable(query.getPageSize()).orElse(10)
        );

        // 获取分页数据
        this.page(page, queryWrapper);

        // 构造返回结构
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("finished", !page.hasNext());
        map.put("list", page.getRecords());

        return Result.success("查询成功", map);
    }

    public Result<Object> addStation(Station station) {
        // 表单验证
        if (StringUtils.isBlank(station.getSitename())) {
            return Result.failure("站点名不能为空");
        }

        // 清除 id
        station.setId(null);

        // 添加站点
        if (this.save(station)) {
            return Result.success("添加成功");
        }
        return Result.failure("添加失败");
    }

    public Result<Object> modifyStation(Station station) {
        // 表单验证
        if (station.getId() == null) {
            return Result.failure("站点 id 不能为空");
        }

        // 修改站点
        if (this.updateById(station)) {
            return Result.success("修改成功");
        }
        return Result.failure("修改失败");
    }

    public Result<Object> deleteStation(DeleteVO deleteVO) {
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

    public Result<Object> nearby(double longitude, double latitude, double distance) {
        if (distance == 0) distance = 200;

        return Result.success(stationMapper.nearby(longitude, latitude, distance));
    }

}
