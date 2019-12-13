package com.spring.tdd.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.tdd.models.Session;
import com.spring.tdd.repositories.SessionRepository;

import javassist.NotFoundException;

@Service
public class SessionService {
	@Autowired
	SessionRepository sessionRepository;

	public List<Session> findAll() {
		return sessionRepository.findAll();
	}

	public Optional<Session> findById(Long id) {
		return sessionRepository.findById(id);
	}

	public void delete(Long id) throws NotFoundException {
		if (!sessionRepository.findById(id).isPresent()) {
			throw new NotFoundException("Session does not exist");
		}
		sessionRepository.deleteById(id);
	}

	public Session edit(Long id, Session session) throws NotFoundException {
		return sessionRepository.findById(id).map(entity -> {
			BeanUtils.copyProperties(session, entity, "session_id");
			return sessionRepository.saveAndFlush(session);
		}).orElseThrow(() -> new NotFoundException("Session does not exist"));
	}

	public Session save(Session session) {
		return sessionRepository.saveAndFlush(session);
	}

}
