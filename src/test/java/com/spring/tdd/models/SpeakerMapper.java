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

import com.spring.tdd.dtos.SpeakerDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@ActiveProfiles("test")
public class SpeakerMapper {

	@Autowired
	ModelMapper modelMapper;

	@Test
	@DisplayName("Test Mapping Speaker")
	public void mapSpeaker() {
		Speaker speaker = new Speaker(1L, "Martin", "Fowler", "Engineer", "", "");
		SpeakerDto speakerDto = new SpeakerDto(1L, "Martin", "Fowler", "Engineer", "", "");

		Speaker result = speaker = modelMapper.map(speakerDto, Speaker.class);

		Assertions.assertSame(result.getFirstName(), speaker.getFirstName(), "First name should be the same");
		Assertions.assertSame(result.getLastName(), speaker.getLastName(), "Last name should be the same");
		Assertions.assertSame(result.getCompany(), speaker.getCompany(), "Company should be the same");
		Assertions.assertSame(result.getSpeakerBio(), speaker.getSpeakerBio(), "Bio should be the same");
		Assertions.assertSame(result.getSpeakerId(), speaker.getSpeakerId(), "Id should be the same");
	}
}
