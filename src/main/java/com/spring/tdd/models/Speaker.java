package com.spring.tdd.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Speakers")
public class Speaker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long speaker_id;
	private String first_name;
	private String last_name;
	private String title;
	private String company;
	private String speaker_bio;
	@ManyToMany(mappedBy = "speakers")
	@JsonIgnore
	private List<Session> sessions;

	public Speaker() {

	}

	public Speaker(Long speaker_id, String first_name, String last_name, String title, String company,
			String speaker_bio) {
		this.speaker_id = speaker_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.title = title;
		this.company = company;
		this.speaker_bio = speaker_bio;
	}

	public Speaker(String first_name, String last_name, String title, String company, String speaker_bio) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.title = title;
		this.company = company;
		this.speaker_bio = speaker_bio;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public Long getSpeaker_id() {
		return speaker_id;
	}

	public String getSpeaker_bio() {
		return speaker_bio;
	}

	public void setSpeaker_bio(String speaker_bio) {
		this.speaker_bio = speaker_bio;
	}

	public void setSpeaker_id(Long speaker_id) {
		this.speaker_id = speaker_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Speaker other = (Speaker) obj;
		if (speaker_id == null) {
			if (other.speaker_id != null) {
				return false;
			}
		} else if (!speaker_id.equals(other.speaker_id)) {
			return false;
		}
		return true;
	}

}
