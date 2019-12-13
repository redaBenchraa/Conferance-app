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

import com.spring.tdd.models.Session;
import com.spring.tdd.services.SessionService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

	@Autowired
	SessionService sessionService;

	@GetMapping
	public List<Session> list() {
		return sessionService.findAll();
	}

	@GetMapping
	@RequestMapping("{id}")
	public ResponseEntity<?> get(@PathVariable Long id) {
		return sessionService.findById(id).map(session -> {
			return ResponseEntity.ok().body(session);
		}).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody final Session session) {
		Session newSession = sessionService.save(session);
		try {
			return ResponseEntity.created(new URI("/api/v1/sessions/" + newSession.getSession_id())).body(newSession);
		} catch (URISyntaxException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody final Session session) {
		try {
			Session newSession = sessionService.edit(id, session);
			return ResponseEntity.ok().body(newSession);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			sessionService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
