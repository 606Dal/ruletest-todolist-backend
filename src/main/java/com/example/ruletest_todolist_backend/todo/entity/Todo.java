package com.example.ruletest_todolist_backend.todo.entity;

public class Todo {

	private Long todoId;
	private String content;
	private boolean completed;

	public Todo(Long todoId, String content, boolean completed) {
		this.todoId = todoId;
		this.content = content;
		this.completed = completed;
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

	public void updateCompleted(boolean completed) {
		this.completed = completed;
	}
}
