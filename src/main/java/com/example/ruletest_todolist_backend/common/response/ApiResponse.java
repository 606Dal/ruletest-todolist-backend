package com.example.ruletest_todolist_backend.common.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private static final DateTimeFormatter RESPONSE_TIME_FORMATTER =
			DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	private final boolean success;
	private final String responseTime;
	private final T data;
	private final Integer code;
	private final String message;

	private ApiResponse(boolean success, T data, Integer code, String message) {
		this.success = success;
		this.responseTime = LocalDateTime.now().format(RESPONSE_TIME_FORMATTER);
		this.data = data;
		this.code = code;
		this.message = message;
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, data, null, null);
	}

	public static <T> ApiResponse<T> failure(int code, String message) {
		return new ApiResponse<>(false, null, code, message);
	}

	public boolean isSuccess() {
		return success;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public T getData() {
		return data;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
