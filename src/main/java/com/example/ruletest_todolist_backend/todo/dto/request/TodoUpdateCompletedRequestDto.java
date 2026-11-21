package com.example.ruletest_todolist_backend.todo.dto.request;

public class TodoUpdateCompletedRequestDto {

	private Boolean completed;

	public TodoUpdateCompletedRequestDto() {
	}

	public TodoUpdateCompletedRequestDto(Boolean completed) {
		this.completed = completed;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
}
