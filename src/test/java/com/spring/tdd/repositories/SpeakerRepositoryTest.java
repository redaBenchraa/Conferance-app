package com.spring.tdd.repositories;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.spring.tdd.models.Speaker;

@ExtendWith({ DBUnitExtension.class, SpringExtension.class })
@SpringBootTest()
@ActiveProfiles("test")
@DataSet(value = "speakers.yml")
public class SpeakerRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	SpeakerRepository repository;

	public ConnectionHolder getConnectionHolder() {
		return () -> dataSource.getConnection();
	}

	@Test
	@DisplayName("Test find all")
	void findAll() {
		List<Speaker> speakers = repository.findAll();
		Assertions.assertSame(speakers.size(), 2, "Should have 2 speakers");
	}

	@Test
	@DisplayName("Test find by id succeeded")
	void findByIdSuccess() {
		Optional<Speaker> speaker = repository.findById(1L);
		Assertions.assertTrue(speaker.isPresent(), "Speaker should be present");
		Speaker s = speaker.get();
		Assertions.assertEquals(s.getSpeaker_id(), 1, "Id Should be 1");
		Assertions.assertEquals(s.getFirst_name(), "Martin", "First name Should be Martin");
		Assertions.assertEquals(s.getLast_name(), "Fowler", "Last Name Should be Fowler");
		Assertions.assertEquals(s.getCompany(), "H", "Company Should be H");
		Assertions.assertEquals(s.getTitle(), "SE", "Title Should be SE");
	}

	@Test
	@DisplayName("Test find by id failed")
	void findByIdFailure() {
		Optional<Speaker> speaker = repository.findById(3L);
		Assertions.assertTrue(!speaker.isPresent(), "Speaker should not be present");
	}

	@Test
	@DisplayName("Test create a new speaker")
	void Create() {
		Speaker s = new Speaker(3L, "Reda", "Ben", "", "", "");
		Speaker result = repository.saveAndFlush(s);
		Assertions.assertNotNull(result, "Speaker should not be null");
		Speaker speaker = repository.getOne(result.getSpeaker_id());
		Assertions.assertEquals(speaker.getFirst_name(), "Reda", "First name Should be Martin");
		Assertions.assertEquals(speaker.getLast_name(), "Ben", "Last Name Should be Fowler");

	}

	@Test
	@DisplayName("Test update a speaker")
	void Update() {
		Speaker s = new Speaker(3L, "Reda", "Ben", "", "", "");
		Speaker result = repository.save(s);
		Assertions.assertNotNull(result, "Speaker should not be null");
		Speaker speaker = repository.getOne(result.getSpeaker_id());
		Assertions.assertEquals(speaker.getFirst_name(), "Reda", "First name Should be Martin");
		Assertions.assertEquals(speaker.getLast_name(), "Ben", "Last Name Should be Fowler");

	}

	@Test
	@DisplayName("Test delete a speaker")
	void Delete() {
		repository.deleteById(1L);
		Optional<Speaker> speaker = repository.findById(1L);
		Assertions.assertFalse(speaker.isPresent(), "Speaker should not be present");
	}

	@Test
	@DisplayName("Test delete a speaker failure")
	void DeleteWithFailure() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> repository.deleteById(1222L));
	}

}
