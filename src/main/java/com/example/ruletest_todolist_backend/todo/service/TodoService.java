package com.example.ruletest_todolist_backend.todo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.ruletest_todolist_backend.common.exception.BusinessException;
import com.example.ruletest_todolist_backend.common.exception.ErrorCode;
import com.example.ruletest_todolist_backend.todo.dto.request.TodoCreateRequestDto;
import com.example.ruletest_todolist_backend.todo.dto.request.TodoUpdateCompletedRequestDto;
import com.example.ruletest_todolist_backend.todo.dto.response.TodoResponseDto;
import com.example.ruletest_todolist_backend.todo.entity.Todo;
import com.example.ruletest_todolist_backend.todo.repository.TodoRepository;

@Service
public class TodoService {

	private static final Logger log = LoggerFactory.getLogger(TodoService.class);

	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public TodoResponseDto createTodo(TodoCreateRequestDto requestDto) {
		validateContent(requestDto.getContent());
		Todo todo = todoRepository.save(requestDto.getContent().trim());
		log.info("할 일 등록 완료. todoId={}", todo.getTodoId());
		return TodoResponseDto.from(todo);
	}

	public List<TodoResponseDto> getTodos() {
		return todoRepository.findAll().stream()
				.map(TodoResponseDto::from)
				.toList();
	}

	public TodoResponseDto updateTodoCompleted(Long todoId, TodoUpdateCompletedRequestDto requestDto) {
		if (requestDto.getCompleted() == null) {
			throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
		}

		Todo todo = todoRepository.findById(todoId)
				.orElseThrow(() -> new BusinessException(ErrorCode.TODO_NOT_FOUND));
		todo.updateCompleted(requestDto.getCompleted());
		log.info("할 일 완료 상태 변경. todoId={}, completed={}", todoId, requestDto.getCompleted());
		return TodoResponseDto.from(todo);
	}

	public void deleteTodo(Long todoId) {
		if (!todoRepository.existsById(todoId)) {
			throw new BusinessException(ErrorCode.TODO_NOT_FOUND);
		}
		todoRepository.deleteById(todoId);
		log.info("할 일 삭제 완료. todoId={}", todoId);
	}

	private void validateContent(String content) {
		if (content == null || content.trim().isEmpty()) {
			throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}
}
