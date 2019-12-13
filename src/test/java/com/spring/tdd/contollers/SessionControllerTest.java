package com.spring.tdd.contollers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.tdd.models.Session;
import com.spring.tdd.services.SessionService;

import javassist.NotFoundException;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class SessionControllerTest {

	@MockBean
	private SessionService sessionService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Get /sessions/1 - Found")
	public void GetSession() throws Exception {
		Session mockSession = new Session(1L, "C++", "Fundamentals", 12);
		doReturn(Optional.of(mockSession)).when(sessionService).findById(1L);
		mockMvc.perform(get("/api/v1/sessions/{id}", 1))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.session_id", is(1)))
				.andExpect(jsonPath("$.session_name", is("C++")))
				.andExpect(jsonPath("$.session_description", is("Fundamentals")))
				.andExpect(jsonPath("$.session_length", is(12)));
	}

	@Test
	@DisplayName("Get /sessions/1 - Not found")
	public void GetSessionNotFound() throws Exception {
		doReturn(Optional.empty()).when(sessionService).findById(1L);
		mockMvc.perform(get("/api/v1/sessions/{id}", 1))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Delete /sessions/1 - Found")
	public void DeleteSessionFound() throws Exception {
		doNothing().when(sessionService).delete(1L);
		mockMvc.perform(delete("/api/v1/sessions/{id}", 1))
				.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("Delete /sessions/1 - NotFound")
	public void DeleteSessionNotFound() throws Exception {
		doThrow(new NotFoundException("Session does not exist")).when(sessionService).delete(1L);
		mockMvc.perform(delete("/api/v1/sessions/{id}", 1))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Post /sessions - Not found")
	public void CreateSession() throws Exception {
		Session mockSession = new Session(1L, "C++", "Fundamentals", 12);
		Session session = new Session("C++", "Fundamentals", 12);
		doReturn(mockSession).when(sessionService).save(session);
		mockMvc.perform(post("/api/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(session)))
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/sessions/1"))
				.andExpect(jsonPath("$.session_id", is(1)))
				.andExpect(jsonPath("$.session_name", is("C++")))
				.andExpect(jsonPath("$.session_description", is("Fundamentals")))
				.andExpect(jsonPath("$.session_length", is(12)));
	}

	@Test
	@DisplayName("Put /sessions/1 - Success")
	public void EditSession() throws Exception {
		Session mockSession = new Session(1L, "C++", "Fundamentals", 12);
		Session mockEditedsession = new Session(1L, "AA", "AA", 12);
		doReturn(mockEditedsession).when(sessionService).edit(1L, mockSession);
		mockMvc.perform(put("/api/v1/sessions/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(mockSession)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.session_id", is(1)))
				.andExpect(jsonPath("$.session_name", is("AA")))
				.andExpect(jsonPath("$.session_description", is("AA")))
				.andExpect(jsonPath("$.session_length", is(12)));
	}

	private String toJson(Session session) {
		try {
			return new ObjectMapper()
					.writer()
					.withDefaultPrettyPrinter()
					.writeValueAsString(session);

		} catch (JsonProcessingException e) {
			return "";
		}
	}

}
