package com.example.ruletest_todolist_backend.todo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.ruletest_todolist_backend.todo.repository.TodoRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
class TodoControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		todoRepository.findAll().forEach(todo -> todoRepository.deleteById(todo.getTodoId()));
	}

	@Test
	void createTodo_success() throws Exception {
		mockMvc.perform(post("/api/v1/todos")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"content\":\"공부하기\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.responseTime").exists())
				.andExpect(jsonPath("$.data.content").value("공부하기"))
				.andExpect(jsonPath("$.data.completed").value(false));
	}

	@Test
	void createTodo_blankContent_returnsBadRequest() throws Exception {
		mockMvc.perform(post("/api/v1/todos")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"content\":\"   \"}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.code").value(4000))
				.andExpect(jsonPath("$.message").value("입력값이 올바르지 않다."));
	}

	@Test
	void getTodos_success() throws Exception {
		mockMvc.perform(post("/api/v1/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"content\":\"운동하기\"}"));

		mockMvc.perform(get("/api/v1/todos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data[0].content").value("운동하기"))
				.andExpect(jsonPath("$.data[0].completed").value(false));
	}

	@Test
	void updateTodoCompleted_success() throws Exception {
		MvcResult createResult = mockMvc.perform(post("/api/v1/todos")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"content\":\"읽기\"}"))
				.andExpect(status().isCreated())
				.andReturn();

		JsonNode created = objectMapper.readTree(createResult.getResponse().getContentAsString());
		long todoId = created.get("data").get("todoId").asLong();

		mockMvc.perform(patch("/api/v1/todos/{todoId}", todoId)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"completed\":true}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.completed").value(true));
	}

	@Test
	void deleteTodo_success() throws Exception {
		MvcResult createResult = mockMvc.perform(post("/api/v1/todos")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"content\":\"삭제할일\"}"))
				.andExpect(status().isCreated())
				.andReturn();

		JsonNode created = objectMapper.readTree(createResult.getResponse().getContentAsString());
		long todoId = created.get("data").get("todoId").asLong();

		mockMvc.perform(delete("/api/v1/todos/{todoId}", todoId))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/api/v1/todos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	void deleteTodo_notFound() throws Exception {
		mockMvc.perform(delete("/api/v1/todos/{todoId}", 999L))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.code").value(4400));
	}
}
