package com.example.ruletest_todolist_backend.todo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.ruletest_todolist_backend.todo.entity.Todo;

@Repository
public class TodoRepository {

	private final ConcurrentHashMap<Long, Todo> todos = new ConcurrentHashMap<>();
	private final AtomicLong sequence = new AtomicLong(1L);

	public Todo save(String content) {
		Long todoId = sequence.getAndIncrement();
		Todo todo = new Todo(todoId, content, false);
		todos.put(todoId, todo);
		return todo;
	}

	public List<Todo> findAll() {
		return new ArrayList<>(todos.values());
	}

	public Optional<Todo> findById(Long todoId) {
		return Optional.ofNullable(todos.get(todoId));
	}

	public void deleteById(Long todoId) {
		todos.remove(todoId);
	}

	public boolean existsById(Long todoId) {
		return todos.containsKey(todoId);
	}
}
