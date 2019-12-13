package com.spring.tdd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.tdd.models.Speaker;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {

}
