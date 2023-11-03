package com.task.todo;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
		if (repository.count() == 0) {
			repository.save(new Item("description 1",new Date()));
			repository.save(new Item("description 2",new Date()));
			repository.save(new Item("description 3",new Date()));
		}
		System.out.println(repository.findAll());
	}

	@Test
	public void shouldAddItem() throws Exception {

		String requestBody = "{\"description\": \"desc. test\", \"dueDate\": \"01.12.2023 12:00:00\"}";
		mockMvc.perform(post("/api/item")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.description").value("desc. test"))
				.andExpect(jsonPath("$.dueDate").value("01.12.2023 12:00:00"));
	}
	@Test
	public void shouldChangeDescription() throws Exception {

		mockMvc.perform(put("/api/item/description/1")
						.param("description","New description"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.description").value("New description"));
	}
	@Test
	public void shouldNotAcceptEmptyDescription() throws Exception {

		mockMvc.perform(put("/api/item/description/1")
						.param("description", ""))
				.andExpect(status().isBadRequest());
	}
	@Test
	public void shouldMarkAsDone() throws Exception {

		mockMvc.perform(put("/api/item/done/1"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.status").value(TaskStatus.DONE.toString()));
	}
	@Test
	public void shouldMarkAsNotDone() throws Exception {

		mockMvc.perform(put("/api/item/not_done/1"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.status").value(TaskStatus.NOT_DONE.toString()));
	}
	@Test
	public void shouldShowAll() throws Exception {

		//making an item to done
		mockMvc.perform(put("/api/item/done/1"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.status").value(TaskStatus.DONE.toString()));
		//showing all including
		mockMvc.perform(get("/api/items").param("showAll", "true"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.itemList", hasSize(4))); // assuming there are 3 items in total
	}
	@Test
	public void shouldOnlyShowNotDone() throws Exception {
		//making an item to done
		mockMvc.perform(put("/api/item/done/1"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.status").value(TaskStatus.DONE.toString()));
		//showing all including
		mockMvc.perform(get("/api/items"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.itemList", hasSize(3))); // assuming there are 3 items in total
	}
	@Test
	public void shouldGetItemDetail() throws Exception {
		mockMvc.perform(get("/api/item/2"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.description").value("description 2"));
	}


}