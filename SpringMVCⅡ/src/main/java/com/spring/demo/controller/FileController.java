package com.spring.demo.controller;

import com.spring.demo.model.ApiResult;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * 文件上传与下载控制器。
 *
 * 文件上传核心：
 *   - 前端：FormData + <input type="file">
 *   - 后端：@RequestParam("file") MultipartFile
 *
 * 文件下载核心：
 *   - ResponseEntity<Resource> 返回文件流
 *   - Content-Disposition 头控制下载行为
 */
@RestController
@RequestMapping("/files")
public class FileController {

    /** 上传文件存储目录（项目 webapp/uploads 下） */
    private static final String UPLOAD_DIR =
            System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "main" + File.separator + "webapp" + File.separator + "uploads";

    // ==================== 文件上传 ====================

    /**
     * POST /files/upload — 单文件上传
     *
     * @param file 上传的文件，参数名必须与前端 <input name="file"> 一致
     *
     * 使用 Postman 测试：
     *   POST http://localhost:8080/spring-mvc-advanced/files/upload
     *   Header: Authorization: demo-token-123
     *   Body → form-data → key=file (类型选 File) → 选择文件
     */
    @PostMapping("/upload")
    public ApiResult<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件为空");
        }

        try {
            // 1. 确保上传目录存在
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 2. 生成唯一文件名（原文件名 + UUID，防止冲突）
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String savedFilename = UUID.randomUUID() + extension;

            // 3. 保存文件
            Path destination = Paths.get(UPLOAD_DIR, savedFilename);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("[FileController] 文件上传成功: " + originalFilename
                    + " → " + savedFilename);

            // 4. 返回文件访问 URL
            Map<String, String> result = new HashMap<>();
            result.put("originalName", originalFilename);
            result.put("savedName", savedFilename);
            result.put("url", "/spring-mvc-advanced/uploads/" + savedFilename);
            result.put("size", file.getSize() + " bytes");

            return ApiResult.success("文件上传成功", result);

        } catch (IOException e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage(), e);
        }
    }

    /**
     * POST /files/upload-multi — 多文件上传
     *
     * @param files 前端多个文件，name 属性相同
     *
     * 使用 Postman 测试：
     *   POST http://localhost:8080/spring-mvc-advanced/files/upload-multi
     *   Body → form-data → key=files (类型选 File) → 选择文件1
     *                     → key=files (类型选 File) → 选择文件2
     */
    @PostMapping("/upload-multi")
    public ApiResult<List<Map<String, String>>> uploadMulti(
            @RequestParam("files") MultipartFile[] files) {

        List<Map<String, String>> results = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;
            try {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                String originalFilename = file.getOriginalFilename();
                String extension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String savedFilename = UUID.randomUUID() + extension;

                Path destination = Paths.get(UPLOAD_DIR, savedFilename);
                Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

                Map<String, String> result = new HashMap<>();
                result.put("originalName", originalFilename);
                result.put("savedName", savedFilename);
                result.put("url", "/spring-mvc-advanced/uploads/" + savedFilename);
                results.add(result);

            } catch (IOException e) {
                throw new RuntimeException("文件上传失败: " + e.getMessage(), e);
            }
        }

        return ApiResult.success("批量上传成功，共 " + results.size() + " 个文件", results);
    }

    // ==================== 文件下载 ====================

    /**
     * GET /files/download/{filename} — 文件下载
     *
     * 通过 ResponseEntity<Resource> 返回文件流。
     * Content-Disposition: attachment 告诉浏览器下载而非预览。
     *
     * @param filename 上传时生成的文件名（含 UUID）
     *
     * 浏览器访问：
     *   http://localhost:8080/spring-mvc-advanced/files/download/{filename}
     */
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        try {
            // 1. 定位文件
            Path filePath = Paths.get(UPLOAD_DIR, filename);
            Resource resource = new FileSystemResource(filePath);

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 2. 探测文件 MIME 类型
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";  // 未知类型默认二进制流
            }

            // 3. 构建响应（Content-Disposition: attachment 强制下载）
            String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8)
                    .replace("+", "%20");  // 空格处理

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename*=UTF-8''" + encodedFilename)
                    .body(resource);

        } catch (IOException e) {
            throw new RuntimeException("文件下载失败: " + e.getMessage(), e);
        }
    }

    /**
     * GET /files/list — 列出已上传的所有文件
     */
    @GetMapping("/list")
    public ApiResult<List<Map<String, Object>>> listFiles() {
        File uploadDir = new File(UPLOAD_DIR);
        List<Map<String, Object>> fileList = new ArrayList<>();

        File[] files = uploadDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    Map<String, Object> info = new HashMap<>();
                    info.put("filename", file.getName());
                    info.put("size", file.length());
                    info.put("url", "/spring-mvc-advanced/uploads/" + file.getName());
                    info.put("downloadUrl",
                            "/spring-mvc-advanced/files/download/" + file.getName());
                    fileList.add(info);
                }
            }
        }

        return ApiResult.success(fileList);
    }
}
