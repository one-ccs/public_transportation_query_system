package com.example.public_transportation_query_system.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

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

    @Operation(summary = "头像上传", description = "头像上传接口（支持上传文件或直接上传 base64）")
    @PostMapping("/upload/avatar")
    public void uploadAvatar(@RequestParam(value = "base64", required = false) String base64, @RequestParam(value = "file", required = false) MultipartFile multipartFile, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        if (multipartFile != null && !multipartFile.isEmpty()) {
            // 处理文件上传
            handleMultipartFileUpload(multipartFile, response);
        }
        else if (!base64.isEmpty()) {
            // 处理 base64 上传
            handleBase64Upload(base64, response);
        }
        else {
            response.getWriter().write(Result.failure("请提供 base64 或 file").toJsonString());
        }

    }

    private void handleMultipartFileUpload(MultipartFile file, HttpServletResponse response) throws IOException {
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
            return;
        }

        response.getWriter().write(Result.success("文件上传成功", safeFilename).toJsonString());
    }

    private void handleBase64Upload(String fileData, HttpServletResponse response) throws IOException {
        // 从 base64 数据中提取文件后缀
        String fileExtension = fileData.substring(fileData.indexOf('/') + 1, fileData.indexOf(';'));

        // 生成安全的文件名
        String safeFilename = RandomStringUtils.randomAlphanumeric(16) + "." + fileExtension;

        // 将 base64 数据转换为字节数组
        byte[] fileBytes = Base64.getDecoder().decode(fileData.split(",")[1]);

        // 创建文件对象
        File uploadedFile = new File(FileUtil.getUploadPathWith("/image/avatar"), safeFilename);

        // 保存到指定路径
        try (FileOutputStream fileOutputStream = new FileOutputStream(uploadedFile)) {
            fileOutputStream.write(fileBytes);
        } catch (IOException e) {
            response.getWriter().write(Result.failure("文件保存失败").toJsonString());
            return;
        }

        response.getWriter().write(Result.success("文件上传成功", safeFilename).toJsonString());
    }

    /**
     * 获取文件扩展名
     * @param fileName 文件名
     * @return
     */
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');

        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

}
