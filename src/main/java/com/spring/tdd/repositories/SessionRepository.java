package com.spring.tdd.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.tdd.models.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

	Optional<Session> findBySessionName(String sessionName);

}
