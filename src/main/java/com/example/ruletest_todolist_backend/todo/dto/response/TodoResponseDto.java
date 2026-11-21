package com.example.ruletest_todolist_backend.todo.dto.response;

import com.example.ruletest_todolist_backend.todo.entity.Todo;

public class TodoResponseDto {

	private final Long todoId;
	private final String content;
	private final boolean completed;

	public TodoResponseDto(Long todoId, String content, boolean completed) {
		this.todoId = todoId;
		this.content = content;
		this.completed = completed;
	}

	public static TodoResponseDto from(Todo todo) {
		return new TodoResponseDto(todo.getTodoId(), todo.getContent(), todo.isCompleted());
	}

	public Long getTodoId() {
		return todoId;
	}

	public String getContent() {
		return content;
	}

	public boolean isCompleted() {
		return completed;
	}
}
