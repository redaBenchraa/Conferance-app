package com.spring.tdd;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.spring.tdd.dtos.SessionDto;
import com.spring.tdd.dtos.SpeakerDto;
import com.spring.tdd.models.Session;
import com.spring.tdd.models.Speaker;

@Configuration
public class MapperConfiguration {
	@Primary
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(SessionDto.class, Session.class).addMappings(mapper -> {
			mapper.map(SessionDto::getId, Session::setSessionId);
			mapper.map(SessionDto::getLength, Session::setSessionLength);
			mapper.map(SessionDto::getName, Session::setSessionName);
			mapper.map(SessionDto::getDescription, Session::setSessionDescription);
		});

		modelMapper.createTypeMap(SpeakerDto.class, Speaker.class).addMappings(mapper -> {
			mapper.map(SpeakerDto::getId, Speaker::setSpeakerId);
			mapper.map(SpeakerDto::getCompanyName, Speaker::setCompany);
			mapper.map(SpeakerDto::getName, Speaker::setFirstName);
			mapper.map(SpeakerDto::getFamilyName, Speaker::setLastName);
			mapper.map(SpeakerDto::getJob, Speaker::setTitle);
			mapper.map(SpeakerDto::getBio, Speaker::setSpeakerBio);
		});

		return modelMapper;
	}
}