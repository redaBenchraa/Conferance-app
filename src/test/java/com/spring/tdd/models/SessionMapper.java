package com.spring.tdd.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.tdd.dtos.SessionDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@ActiveProfiles("test")
public class SessionMapper {

	@Autowired
	ModelMapper modelMapper;

	@Test
	@DisplayName("Test Mapping Session")
	public void mapSession() {
		Session session = new Session(1L, "C++", "Fundamentals", 12);
		SessionDto sessionDto = new SessionDto(1L, "C++", "Fundamentals", 12);

		Session result = session = modelMapper.map(sessionDto, Session.class);

		Assertions.assertSame(result.getSessionName(), session.getSessionName(), "Namme should be the same");
		Assertions.assertSame(result.getSessionDescription(), session.getSessionDescription(),
				"getDescription should be the same");
		Assertions.assertSame(result.getSessionLength(), session.getSessionLength(), "Length should be the same");
		Assertions.assertSame(result.getSessionId(), session.getSessionId(), "Id should be the same");
	}
}
