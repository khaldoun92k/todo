package com.task.todo;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ItemRepository repository;
	@BeforeEach
	public void setup() throws Exception {
		repository.save(new Item("description",new Date()));
	}

	@Test
	public void shouldAddItem() throws Exception {
		String requestBody = "{\"description\": \"desc. test\", \"dueDate\": \"01.12.2023 12:00:00\"}";
		mockMvc.perform(post("/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.description").value("desc. test"))
				.andExpect(jsonPath("$.dueDate").value("01.12.2023 12:00:00"));
	}
	@Test
	public void shouldChangeDescription() throws Exception {
		mockMvc.perform(put("/items/description/1")
						.param("description","New description"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.description").value("New description"));
	}
	@Test
	public void shouldNotAcceptEmptyDescription() throws Exception {
		mockMvc.perform(put("/items/description/1")
						.param("description","New description"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.description").value("New description"));
	}
	@Test
	public void shouldMarkAsDone() throws Exception {
		mockMvc.perform(put("/items/done/1"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.status").value(TaskStatus.DONE.toString()));
	}
	@Test
	public void shouldMarkAsNotDone() throws Exception {
		mockMvc.perform(put("/items/not_done/1"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.status").value(TaskStatus.NOT_DONE.toString()));
	}
	@Test
	public void shouldShowAll() throws Exception {
	//TODO test needs to be added
	}
	@Test
	public void shouldOnlyShowNotDone() throws Exception {
	//TODO test needs to be added
	}
	@Test
	public void shouldGetItemDetail() throws Exception {
	//TODO test needs to be added
	}

}