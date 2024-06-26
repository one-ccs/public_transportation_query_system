package com.example.public_transportation_query_system.service.impl;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.dto.MyUserDetails;
import com.example.public_transportation_query_system.entity.po.Notice;
import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.DeleteVO;
import com.example.public_transportation_query_system.entity.vo.response.NoticeVO;
import com.example.public_transportation_query_system.mapper.NoticeMapper;
import com.example.public_transportation_query_system.service.INoticeService;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    public Result<Object> getNoticePage(BasePageQuery query) {
        // 构造查询条件
        QueryWrapper<NoticeVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge(query.getStartDatetime() != null, "release_datetime", query.getStartDatetime())
            .le(query.getEndDatetime() != null, "release_datetime", query.getEndDatetime())
            .and(StringUtils.isNotBlank(query.getQuery()), i -> i
                .like("username", query.getQuery())
                .or()
                .like("title", query.getQuery())
                .or()
                .like("content", query.getQuery())
            );

        // 分页
        Page<NoticeVO> page = new Page<>(
            Optional.ofNullable(query.getPageIndex()).orElse(1),
            Optional.ofNullable(query.getPageSize()).orElse(10)
        );

        // 获取分页数据
        noticeMapper.selectPage(page, queryWrapper);

        // 构造返回结构
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("list", page.getRecords());

        return Result.success("查询成功", map);
    }

    public Result<Object> addNotice(Notice notice) {
        // 表单验证
        if (StringUtils.isBlank(notice.getContent())) {
            return Result.failure("公告内容不能为空");
        }

        // 清除 id、发布日期
        notice.setId(null);
        notice.setReleaseDatetime(null);

        // 设置发布用户 id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails)authentication.getPrincipal();
        notice.setUserId(myUserDetails.getUser().getId());

        // 添加公告
        if (this.save(notice)) {
            return Result.success("添加成功");
        }
        return Result.failure("添加失败");
    }

    public Result<Object> modifyNotice(Notice notice) {
        // 表单验证
        if (notice.getId() == null) {
            return Result.failure("公告 id 不能为空");
        }

        // 清除发布用户 id、发布日期
        notice.setUserId(null);
        notice.setReleaseDatetime(null);

        // 修改公告
        if (this.updateById(notice)) {
            return Result.success("修改成功");
        }
        return Result.failure("修改失败");
    }

    public Result<Object> deleteNotice(DeleteVO deleteVO) {
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
