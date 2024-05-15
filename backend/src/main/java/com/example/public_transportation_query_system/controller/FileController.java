package com.example.public_transportation_query_system.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.util.FileUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "7-文件", description = "文件上传下载接口")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Operation(summary = "静态资源获取", description = "静态资源获取接口")
    @GetMapping(value = { "/{type}/{folder}/{filename}", "/{type}/{folder}", "/{type}" }, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Object> resource(@PathVariable String type, @PathVariable(required = false) String folder, @PathVariable(required = false) String filename) {
        String filePath = "/" + type;

        // 组合路径
        if (folder != null) filePath += "/" + folder;
        if (filename != null) filePath += "/" + filename;

        if (!FileUtil.isSafeUploadFile(filePath)) {
            // 文件不存在或不在上传目录，不允许下载
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Result.failure("文件不存在"));
        }
        FileSystemResource file = new FileSystemResource(FileUtil.getUploadPathWith(filePath));;

        return ResponseEntity.ok(file);
    }

    @Operation(summary = "头像上传", description = "头像上传接口")
    @PostMapping("/upload/avatar")
    public void uploadAvatar(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        // 检查文件是否为空
        if (file.isEmpty()) {
            response.getWriter().write(Result.failure("上传文件为空").toJsonString());
            return;
        }

        // 获取文件后缀
        String fileExtension = getFileExtension(file.getOriginalFilename());

        // 生成安全的文件名
        String safeFilename = RandomStringUtils.randomAlphanumeric(16) + "." + fileExtension;

        // 创建文件对象
        File uploadedFile = new File(FileUtil.getUploadPathWith("/image/avatar"), safeFilename);;

        // 保存到指定路径
        try (FileOutputStream fileOutputStream = new FileOutputStream(uploadedFile)) {
            fileOutputStream.write(file.getBytes());
        } catch (IOException e) {
            response.getWriter().write(Result.failure("文件保存失败").toJsonString());
        }

        response.getWriter().write(Result.success("文件上传成功", safeFilename).toJsonString());
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');

        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

}
