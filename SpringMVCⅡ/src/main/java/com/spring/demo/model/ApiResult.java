package com.spring.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

/**
 * 统一 API 响应结果封装。
 *
 * RESTful 最佳实践：所有接口返回统一的数据结构，便于前端统一处理。
 *
 * 示例响应 JSON：
 * {
 *   "code": 200,
 *   "message": "操作成功",
 *   "data": { ... },
 *   "timestamp": "2026-07-16T10:30:00"
 * }
 *
 * @param <T> data 字段的泛型类型
 */
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 值不参与序列化
public class ApiResult<T> {

    /** 状态码（200 成功，400 客户端错误，500 服务器错误） */
    private int code;

    /** 提示消息 */
    private String message;

    /** 响应数据（泛型，可以是任意类型） */
    private T data;

    /** 时间戳 */
    private LocalDateTime timestamp;

    // ==================== 静态工厂方法 ====================

    /** 成功响应（带数据） */
    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.code = 200;
        result.message = "操作成功";
        result.data = data;
        result.timestamp = LocalDateTime.now();
        return result;
    }

    /** 成功响应（无数据） */
    public static <T> ApiResult<T> success() {
        return success(null);
    }

    /** 成功响应（自定义消息） */
    public static <T> ApiResult<T> success(String message, T data) {
        ApiResult<T> result = new ApiResult<>();
        result.code = 200;
        result.message = message;
        result.data = data;
        result.timestamp = LocalDateTime.now();
        return result;
    }

    /** 失败响应 */
    public static <T> ApiResult<T> error(int code, String message) {
        ApiResult<T> result = new ApiResult<>();
        result.code = code;
        result.message = message;
        result.timestamp = LocalDateTime.now();
        return result;
    }

    // ==================== Getter/Setter ====================

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
