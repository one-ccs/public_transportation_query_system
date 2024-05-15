package com.example.public_transportation_query_system.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.util.FileUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "7-文件", description = "文件上传下载接口")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Operation(summary = "静态资源获取", description = "静态资源获取接口")
    @GetMapping(value = { "/{type}/{folder}/{filename}", "/{type}/{folder}", "/{type}" })
    public ResponseEntity<Object> resource(@PathVariable String type, @PathVariable(required = false) String folder, @PathVariable(required = false) String filename) {
        String filePath = "/" + type;

        if (folder != null) filePath += "/" + folder;
        if (filename != null) filePath += "/" + filename;

        if (!FileUtil.isSafeUploadFile(filePath)) {
            // 文件不存在或不在上传目录，不允许下载
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Result.failure("文件不存在"));
        }
        FileSystemResource file = new FileSystemResource(FileUtil.getUploadPathWith(filePath));;

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(file);
    }

    @Operation(summary = "头像上传", description = "头像上传接口")
    @PostMapping("/upload/avatar")
    public void uploadAvatar() {

    }

}
