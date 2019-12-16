package com.spring.tdd.models;

import java.util.List;

import javax.persistence.Column;
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
	@Column(name = "speaker_id")
	private Long speakerId;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "title")
	private String title;
	@Column(name = "company")
	private String company;
	@Column(name = "speaker_bio")
	private String speakerBio;
	@ManyToMany(mappedBy = "speakers")
	@JsonIgnore
	private List<Session> sessions;

	public Speaker() {

	}

	public Speaker(Long speakerId, String firstName, String lastName, String title, String company, String speakerBio) {
		this(firstName, lastName, title, company, speakerBio);
		this.speakerId = speakerId;
	}

	public Speaker(String firstName, String lastName, String title, String company, String speakerBio) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.company = company;
		this.speakerBio = speakerBio;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public Long getSpeakerId() {
		return speakerId;
	}

	public String getSpeakerBio() {
		return speakerBio;
	}

	public void setSpeakerBio(String speakerBio) {
		this.speakerBio = speakerBio;
	}

	public void setSpeakerId(Long speakerId) {
		this.speakerId = speakerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
		result = prime * result + ((speakerId == null) ? 0 : speakerId.hashCode());
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
		if (speakerId == null) {
			if (other.speakerId != null) {
				return false;
			}
		} else {
			if (!speakerId.equals(other.speakerId)) {
				return false;
			}
		}
		return true;
	}

}
