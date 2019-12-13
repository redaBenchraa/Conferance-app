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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((first_name == null) ? 0 : first_name.hashCode());
		result = prime * result + ((last_name == null) ? 0 : last_name.hashCode());
		result = prime * result + ((sessions == null) ? 0 : sessions.hashCode());
		result = prime * result + ((speaker_bio == null) ? 0 : speaker_bio.hashCode());
		result = prime * result + ((speaker_id == null) ? 0 : speaker_id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
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
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (first_name == null) {
			if (other.first_name != null)
				return false;
		} else if (!first_name.equals(other.first_name))
			return false;
		if (last_name == null) {
			if (other.last_name != null)
				return false;
		} else if (!last_name.equals(other.last_name))
			return false;
		if (sessions == null) {
			if (other.sessions != null)
				return false;
		} else if (!sessions.equals(other.sessions))
			return false;
		if (speaker_bio == null) {
			if (other.speaker_bio != null)
				return false;
		} else if (!speaker_bio.equals(other.speaker_bio))
			return false;
		if (speaker_id == null) {
			if (other.speaker_id != null)
				return false;
		} else if (!speaker_id.equals(other.speaker_id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
