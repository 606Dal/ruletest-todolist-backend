package com.example.ruletest_todolist_backend.todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ruletest_todolist_backend.common.response.ApiResponse;
import com.example.ruletest_todolist_backend.todo.dto.request.TodoCreateRequestDto;
import com.example.ruletest_todolist_backend.todo.dto.request.TodoUpdateCompletedRequestDto;
import com.example.ruletest_todolist_backend.todo.dto.response.TodoResponseDto;
import com.example.ruletest_todolist_backend.todo.service.TodoService;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<TodoResponseDto>> createTodo(
			@RequestBody TodoCreateRequestDto requestDto) {
		TodoResponseDto responseDto = todoService.createTodo(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseDto));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<TodoResponseDto>>> getTodos() {
		List<TodoResponseDto> responseDtos = todoService.getTodos();
		return ResponseEntity.ok(ApiResponse.success(responseDtos));
	}

	@PatchMapping("/{todoId}")
	public ResponseEntity<ApiResponse<TodoResponseDto>> updateTodoCompleted(
			@PathVariable Long todoId,
			@RequestBody TodoUpdateCompletedRequestDto requestDto) {
		TodoResponseDto responseDto = todoService.updateTodoCompleted(todoId, requestDto);
		return ResponseEntity.ok(ApiResponse.success(responseDto));
	}

	@DeleteMapping("/{todoId}")
	public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId) {
		todoService.deleteTodo(todoId);
		return ResponseEntity.noContent().build();
	}
}
