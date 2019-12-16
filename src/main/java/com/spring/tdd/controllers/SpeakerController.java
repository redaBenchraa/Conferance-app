package com.spring.tdd.controllers;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.tdd.dtos.SpeakerDto;
import com.spring.tdd.models.Speaker;
import com.spring.tdd.services.SpeakerService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {

	@Autowired
	SpeakerService speakerService;

	@GetMapping
	public List<Speaker> list() {
		return speakerService.findAll();
	}

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("{id}")
	public ResponseEntity<Speaker> get(@PathVariable Long id) {
		return speakerService.findById(id).map(speaker -> ResponseEntity.ok().body(speaker))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Speaker> create(@RequestBody final SpeakerDto speaker) {
		Speaker newSpeaker = speakerService.save(this.convertToEntity(speaker));
		try {
			return ResponseEntity.created(new URI("/api/v1/speakers/" + newSpeaker.getSpeakerId())).body(newSpeaker);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<Speaker> edit(@PathVariable Long id, @RequestBody final SpeakerDto speaker) {
		try {
			Speaker newSpeaker = speakerService.edit(id, this.convertToEntity(speaker));
			return ResponseEntity.ok().body(newSpeaker);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Speaker> delete(@PathVariable Long id) {
		try {
			speakerService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private Speaker convertToEntity(SpeakerDto speaker) {
		return modelMapper.map(speaker, Speaker.class);
	}

}
