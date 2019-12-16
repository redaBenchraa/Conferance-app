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

import com.spring.tdd.dtos.SessionDto;
import com.spring.tdd.models.Session;
import com.spring.tdd.services.SessionService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

	@Autowired
	SessionService sessionService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<Session> list() {
		return sessionService.findAll();
	}

	@GetMapping("{id}")
	public ResponseEntity<Session> get(@PathVariable Long id) {
		return sessionService.findById(id).map(session -> ResponseEntity.ok().body(session))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Session> create(@RequestBody final SessionDto session) {
		Session newSession = sessionService.save(this.convertToEntity(session));
		try {
			return ResponseEntity.created(new URI("/api/v1/sessions/" + newSession.getSessionId())).body(newSession);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<Session> edit(@PathVariable Long id, @RequestBody final SessionDto session) {
		try {
			Session newSession = sessionService.edit(id, this.convertToEntity(session));
			return ResponseEntity.ok().body(newSession);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Session> delete(@PathVariable Long id) {
		try {
			sessionService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private Session convertToEntity(SessionDto speaker) {
		return modelMapper.map(speaker, Session.class);
	}

}
