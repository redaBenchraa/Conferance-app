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
import com.spring.tdd.models.Session;

@ExtendWith({ DBUnitExtension.class, SpringExtension.class })
@SpringBootTest()
@ActiveProfiles("test")
@DataSet(value = "sessions.yml")
public class SessionRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	SessionRepository repository;

	public ConnectionHolder getConnectionHolder() {
		return () -> dataSource.getConnection();
	}

	@Test
	@DisplayName("Test find all")
	void findAll() {
		List<Session> sessions = repository.findAll();
		Assertions.assertSame(sessions.size(), 2, "Should have 2 sessions");
	}

	@Test
	@DisplayName("Test find by id succeeded")
	void findByIdSuccess() {
		Optional<Session> session = repository.findById(1L);
		Assertions.assertTrue(session.isPresent(), "Session should be present");
		Session s = session.get();
		Assertions.assertEquals(s.getSession_id(), 1, "Id Should be 1");
		Assertions.assertEquals(s.getSession_length(), 12, "Length Should be 12");
		Assertions.assertEquals(s.getSession_name(), "C++", "Name Should be C++");
		Assertions.assertEquals(s.getSession_description(), "Description", "Description Should be Description");
	}

	@Test
	@DisplayName("Test find by id failed")
	void findByIdFailure() {
		Optional<Session> session = repository.findById(3L);
		Assertions.assertTrue(!session.isPresent(), "Session should not be present");
	}

	@Test
	@DisplayName("Test create a new session")
	void Create() {
		Session s = new Session(3L, "Java", "Steams", 22);
		Session result = repository.saveAndFlush(s);
		Assertions.assertNotNull(result, "Session should not be null");
		Session session = repository.getOne(result.getSession_id());
		Assertions.assertSame(session.getSession_name(), "Java", "Name should be java");
		Assertions.assertSame(session.getSession_description(), "Steams", "Description should be java");
		Assertions.assertSame(session.getSession_length(), 22, "Length should be 22");
	}

	@Test
	@DisplayName("Test update a session")
	void Update() {
		Session s = new Session(2L, "Java", "Steams", 22);
		Session result = repository.save(s);
		Assertions.assertNotNull(result, "Session should not be null");
		Session session = repository.getOne(result.getSession_id());
		Assertions.assertSame(session.getSession_name(), "Java", "Name should be java");
		Assertions.assertSame(session.getSession_description(), "Steams", "Description should be java");
		Assertions.assertSame(session.getSession_length(), 22, "Length should be 22");
	}

	@Test
	@DisplayName("Test delete a session")
	void Delete() {
		repository.deleteById(1L);
		Optional<Session> session = repository.findById(1L);
		Assertions.assertFalse(session.isPresent(), "Session should not be present");
	}

	@Test
	@DisplayName("Test delete a session failure")
	void DeleteWithFailure() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> repository.deleteById(1222L));
	}

}
