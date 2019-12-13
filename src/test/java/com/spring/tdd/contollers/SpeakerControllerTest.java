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
import com.spring.tdd.models.Speaker;
import com.spring.tdd.services.SpeakerService;

import javassist.NotFoundException;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class SpeakerControllerTest {

	@MockBean
	private SpeakerService speakerService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Get /speakers/1 - Found")
	public void GetSpeaker() throws Exception {
		Speaker mockSpeaker = new Speaker(1L, "Martin", "Fowler", "Engineer", "", "");
		doReturn(Optional.of(mockSpeaker)).when(speakerService).findById(1L);
		mockMvc.perform(get("/api/v1/speakers/{id}", 1))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.speaker_id", is(1)))
				.andExpect(jsonPath("$.first_name", is("Martin")))
				.andExpect(jsonPath("$.last_name", is("Fowler")))
				.andExpect(jsonPath("$.title", is("Engineer")));
	}

	@Test
	@DisplayName("Get /speakers/1 - Not found")
	public void GetSpeakerNotFound() throws Exception {
		doReturn(Optional.empty()).when(speakerService).findById(1L);
		mockMvc.perform(get("/api/v1/speakers/{id}", 1))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Delete /speakers/1 - Found")
	public void DeleteSpeakerFound() throws Exception {
		doNothing().when(speakerService).delete(1L);
		mockMvc.perform(delete("/api/v1/speakers/{id}", 1))
				.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("Delete /speakers/1 - NotFound")
	public void DeleteSpeakerNotFound() throws Exception {
		doThrow(new NotFoundException("Speaker does not exist")).when(speakerService).delete(1L);
		mockMvc.perform(delete("/api/v1/speakers/{id}", 1))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Post /speakers - Not found")
	public void CreateSpeaker() throws Exception {
		Speaker mockSpeaker = new Speaker(1L, "Martin", "Fowler", "Engineer", "", "");
		Speaker speaker = new Speaker("Martin", "Fowler", "Engineer", "", "");
		doReturn(mockSpeaker).when(speakerService).save(speaker);
		mockMvc.perform(post("/api/v1/speakers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(speaker)))
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/speakers/1"))
				.andExpect(jsonPath("$.speaker_id", is(1)))
				.andExpect(jsonPath("$.first_name", is("Martin")))
				.andExpect(jsonPath("$.last_name", is("Fowler")))
				.andExpect(jsonPath("$.title", is("Engineer")));
	}

	@Test
	@DisplayName("Put /speakers/1 - Success")
	public void EditSpeaker() throws Exception {
		Speaker mockSpeaker = new Speaker(1L, "Martin", "Fowler", "Engineer", "", "");
		Speaker mockEditedspeaker = new Speaker(1L, "AA", "AA", "Engineer", "", "");
		doReturn(mockEditedspeaker).when(speakerService).edit(1L, mockSpeaker);
		mockMvc.perform(put("/api/v1/speakers/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(mockSpeaker)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.speaker_id", is(1)))
				.andExpect(jsonPath("$.first_name", is("AA")))
				.andExpect(jsonPath("$.last_name", is("AA")))
				.andExpect(jsonPath("$.title", is("Engineer")));
	}

	private String toJson(Speaker speaker) {
		try {
			return new ObjectMapper()
					.writer()
					.withDefaultPrettyPrinter()
					.writeValueAsString(speaker);

		} catch (JsonProcessingException e) {
			return "";
		}
	}

}
