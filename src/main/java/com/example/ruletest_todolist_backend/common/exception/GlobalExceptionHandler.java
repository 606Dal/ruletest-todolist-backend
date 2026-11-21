package com.example.ruletest_todolist_backend.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.ruletest_todolist_backend.common.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		return ResponseEntity.status(errorCode.getHttpStatus())
				.body(ApiResponse.failure(errorCode.getCode(), errorCode.getMessage()));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException exception) {
		log.warn("요청 본문 파싱 실패");
		ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
		return ResponseEntity.status(errorCode.getHttpStatus())
				.body(ApiResponse.failure(errorCode.getCode(), errorCode.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
		log.error("서버 내부 오류", exception);
		return ResponseEntity.internalServerError()
				.body(ApiResponse.failure(5000, "서버 내부 오류가 발생했다."));
	}
}
