package com.example.ruletest_todolist_backend.todo.dto.request;

public class TodoCreateRequestDto {

	private String content;

	public TodoCreateRequestDto() {
	}

	public TodoCreateRequestDto(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
