package com.spring.tdd.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.tdd.models.Session;
import com.spring.tdd.repositories.SessionRepository;

import javassist.NotFoundException;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@ActiveProfiles("test")
public class SessionServiceTest {

	@MockBean
	SessionRepository repository;

	@Autowired
	SessionService service;

	@Test
	@DisplayName("Test find by id - Success")
	public void testFindById() {
		Session mockSession = new Session(1L, "C++", "Fundamentals", 12);
		doReturn(Optional.of(mockSession)).when(repository).findById(1L);

		Optional<Session> result = service.findById(1L);

		Assertions.assertTrue(result.isPresent(), "Session was not found");
		Assertions.assertSame(result.get(), mockSession, "Sessions should be the same");
	}

	@Test
	@DisplayName("Test find by id - Failure")
	public void testFindByIdFailure() {
		doReturn(Optional.empty()).when(repository).findById(1L);

		Optional<Session> result = service.findById(1L);

		Assertions.assertTrue(!result.isPresent(), "Session was found");
	}

	@Test
	@DisplayName("Test all find - Success")
	public void testFindAll() {
		Session mockSession1 = new Session(1L, "C++", "Fundamentals", 12);
		Session mockSession2 = new Session(1L, "C++", "Fundamentals", 12);
		List<Session> mockSessionList = new ArrayList<>();
		mockSessionList.add(mockSession1);
		mockSessionList.add(mockSession2);

		doReturn(mockSessionList).when(repository).findAll();

		List<Session> result = service.findAll();

		Assertions.assertSame(result.size(), mockSessionList.size(), "Shoudl return 2 sessions");
	}

	@Test
	@DisplayName("Test save session - Success")
	public void testSave() {
		Session mockSession = new Session(1L, "C++", "Fundamentals", 12);

		doReturn(mockSession).when(repository).saveAndFlush(any());

		Session result = service.save(mockSession);

		Assertions.assertNotNull(result, "Sessions should not be null");
		Assertions.assertSame(result.getSession_id(), 1L, "Sessions should have id 1");
	}

	@Test
	@DisplayName("Test update session - Success")
	public void testUpdateSuccess() throws NotFoundException {
		Session mockSession = new Session(1L, "C++", "Fundamentals", 12);

		doReturn(Optional.of(mockSession)).when(repository).findById(1L);
		doReturn(mockSession).when(repository).saveAndFlush(any());

		Session result = service.edit(1L, mockSession);

		Assertions.assertNotNull(result, "Sessions should not be null");
		Assertions.assertSame(result.getSession_id(), 1L, "Sessions should have id 1");
	}

	@Test
	@DisplayName("Test update session - Failure")
	public void testUpdateFailure() throws NotFoundException {
		Session mockSession = new Session(1L, "C++", "Fundamentals", 12);

		doReturn(Optional.empty()).when(repository).findById(1L);

		Assertions.assertThrows(NotFoundException.class, () -> service.edit(1L, mockSession), "Should throw exception");
	}

	@Test
	@DisplayName("Test delete session - Success")
	public void testDeleteSuccess() throws NotFoundException {
		Session mockSession = new Session(1L, "C++", "Fundamentals", 12);

		doReturn(Optional.of(mockSession)).when(repository).findById(1L);
		service.delete(1L);
		verify(repository).deleteById(1L);
	}

	@Test
	@DisplayName("Test delete session - Failure")
	public void testDeleteFailure() throws NotFoundException {

		doReturn(Optional.empty()).when(repository).findById(1L);

		Assertions.assertThrows(NotFoundException.class, () -> service.delete(1L), "Should throw exception");
	}

}
