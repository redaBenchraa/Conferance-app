package com.spring.tdd.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.tdd.models.Speaker;
import com.spring.tdd.repositories.SpeakerRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class SpeakerServiceTest {

	@MockBean
	SpeakerRepository repository;

	@Autowired
	SpeakerService service;

	@Test
	@DisplayName("Test find by id - Success")
	public void testFindById() {
		Speaker mockSpeaker = new Speaker(1L, "Martin", "Fowler", "Engineer", "", "");
		doReturn(Optional.of(mockSpeaker)).when(repository).findById(1L);

		Optional<Speaker> result = service.findById(1L);

		Assertions.assertTrue(result.isPresent(), "Speaker was not found");
		Assertions.assertSame(result.get(), mockSpeaker, "Speakers should be the same");
	}

	@Test
	@DisplayName("Test find by id - Failure")
	public void testFindByIdFailure() {
		doReturn(Optional.empty()).when(repository).findById(1L);

		Optional<Speaker> result = service.findById(1L);

		Assertions.assertTrue(!result.isPresent(), "Speaker was found");
	}

	@Test
	@DisplayName("Test all find - Success")
	public void testFindAll() {
		Speaker mockSpeaker1 = new Speaker(1L, "Martin", "Fowler", "Engineer", "", "");
		Speaker mockSpeaker2 = new Speaker(2L, "Martin", "Fowler", "Engineer", "", "");
		List<Speaker> mockSpeakerList = new ArrayList<>();
		mockSpeakerList.add(mockSpeaker1);
		mockSpeakerList.add(mockSpeaker2);

		doReturn(mockSpeakerList).when(repository).findAll();

		List<Speaker> result = service.findAll();

		Assertions.assertSame(result.size(), mockSpeakerList.size(), "Shoudl return 2 speakers");
	}

	@Test
	@DisplayName("Test save speaker - Success")
	public void testSave() {
		Speaker mockSpeaker = new Speaker(1L, "Martin", "Fowler", "Engineer", "", "");

		doReturn(mockSpeaker).when(repository).saveAndFlush(any());

		Speaker result = service.save(mockSpeaker);

		Assertions.assertNotNull(result, "Speakers should not be null");
		Assertions.assertSame(result.getSpeaker_id(), 1L, "Speakers should have id 1");
	}

}
