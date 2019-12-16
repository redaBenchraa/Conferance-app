package com.spring.tdd.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.tdd.models.Speaker;
import com.spring.tdd.repositories.SpeakerRepository;

import javassist.NotFoundException;

@Service
public class SpeakerService {

	@Autowired
	SpeakerRepository speakerRepository;

	public List<Speaker> findAll() {
		return speakerRepository.findAll();
	}

	public Optional<Speaker> findById(Long id) {
		return speakerRepository.findById(id);
	}

	public void delete(Long id) throws NotFoundException {
		if (!speakerRepository.findById(id).isPresent()) {
			throw new NotFoundException("Speaker does not exist");
		}
		speakerRepository.deleteById(id);
	}

	public Speaker edit(Long id, Speaker speaker) throws NotFoundException {
		return speakerRepository.findById(id).map(entity -> {
			BeanUtils.copyProperties(speaker, entity, "speaker_id");
			return speakerRepository.saveAndFlush(speaker);
		}).orElseThrow(() -> new NotFoundException("Speaker does not exist"));
	}

	public Speaker save(Speaker speaker) {
		return speakerRepository.saveAndFlush(speaker);
	}

}
