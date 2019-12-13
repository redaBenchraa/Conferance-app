package com.spring.tdd.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping
	@RequestMapping("{id}")
	public ResponseEntity<?> get(@PathVariable Long id) {
		return speakerService.findById(id).map(speaker -> {
			return ResponseEntity.ok().body(speaker);
		}).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody final Speaker speaker) {
		Speaker newSpeaker = speakerService.save(speaker);
		try {
			return ResponseEntity.created(new URI("/api/v1/speakers/" + newSpeaker.getSpeaker_id())).body(newSpeaker);
		} catch (URISyntaxException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody final Speaker speaker) {
		try {
			Speaker newSpeaker = speakerService.edit(id, speaker);
			return ResponseEntity.ok().body(newSpeaker);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			speakerService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
