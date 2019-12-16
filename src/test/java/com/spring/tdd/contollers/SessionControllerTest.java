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

import java.util.ArrayList;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.tdd.dtos.SessionDto;
import com.spring.tdd.models.Session;
import com.spring.tdd.services.SessionService;

import javassist.NotFoundException;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
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
				.andExpect(jsonPath("$.sessionId", is(1)))
				.andExpect(jsonPath("$.sessionName", is("C++")))
				.andExpect(jsonPath("$.sessionDescription", is("Fundamentals")))
				.andExpect(jsonPath("$.sessionLength", is(12)));
	}

	@Test
	@DisplayName("Get /speakers ")
	public void GetSpeakers() throws Exception {
		Session mockSession = new Session(1L, "C++", "Fundamentals", 12);
		Session mockSession2 = new Session(1L, "C++", "Fundamentals", 12);
		ArrayList<Session> mockList = new ArrayList<>();
		mockList.add(mockSession);
		mockList.add(mockSession2);
		doReturn(mockList).when(sessionService).findAll();
		mockMvc.perform(get("/api/v1/sessions"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].sessionId", is(1)))
				.andExpect(jsonPath("$.[0].sessionName", is("C++")))
				.andExpect(jsonPath("$.[0].sessionDescription", is("Fundamentals")))
				.andExpect(jsonPath("$.[0].sessionLength", is(12)));
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
	@DisplayName("Post /sessions - Success")
	public void CreateSession() throws Exception {
		Session mockSession = new Session(1L, "C++", "Fundamentals", 12);
		Session session = new Session("C++", "Fundamentals", 12);
		doReturn(mockSession).when(sessionService).save(session);
		mockMvc.perform(post("/api/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(session)))
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/sessions/1"))
				.andExpect(jsonPath("$.sessionId", is(1)))
				.andExpect(jsonPath("$.sessionName", is("C++")))
				.andExpect(jsonPath("$.sessionDescription", is("Fundamentals")))
				.andExpect(jsonPath("$.sessionLength", is(12)));
	}

	@Test
	@DisplayName("Post /sessions - Failure")
	public void CreateSessionFailure() throws Exception {
		Session session = new Session("C++", "Fundamentals", 12);
		SessionDto mockSessionDto = new SessionDto(1L, "C++", "Fundamentals", 12);
		doReturn(null).when(sessionService).save(session);
		mockMvc.perform(post("/api/v1/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(mockSessionDto)))
				.andExpect(status().is5xxServerError());
	}

	@Test
	@DisplayName("Put /sessions/1 - Success")
	public void EditSession() throws Exception {
		Session mockSession = new Session(1L, "C++", "Fundamentals", 12);
		Session mockEditedsession = new Session(1L, "AA", "AA", 12);
		SessionDto mockSessionDto = new SessionDto(1L, "C++", "Fundamentals", 12);
		doReturn(mockEditedsession).when(sessionService).edit(1L, mockSession);
		mockMvc.perform(put("/api/v1/sessions/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(toJson(mockSessionDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sessionId", is(1)))
				.andExpect(jsonPath("$.sessionName", is("AA")))
				.andExpect(jsonPath("$.sessionDescription", is("AA")))
				.andExpect(jsonPath("$.sessionLength", is(12)));
	}

	@Test
	@DisplayName("Put /sessions/1 - Failure")
	public void EditSessionFailure() throws Exception {
		Session mockSession = new Session(1L, "C++", "Fundamentals", 12);
		SessionDto mockSessionDto = new SessionDto(1L, "C++", "Fundamentals", 12);

		doThrow(NotFoundException.class).when(sessionService).edit(1L, mockSession);
		mockMvc.perform(put("/api/v1/sessions/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(mockSessionDto)))
				.andExpect(status().isNotFound());
	}

	private String toJson(Object session) {
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
