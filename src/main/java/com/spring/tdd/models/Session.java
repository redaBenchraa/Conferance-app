package com.spring.tdd.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name = "Sessions")
public class Session {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long session_id;
	private String session_name;
	private String session_description;
	private Integer session_length;
	@ManyToMany
	@JoinTable(name = "session_speakers", joinColumns = @JoinColumn(name = "session_id"), inverseJoinColumns = @JoinColumn(name = "speaker_id"))
	private List<Speaker> speakers;

	public Session() {

	}

	public Session(Long session_id, String sessionName, String session_description, Integer session_length) {
		this.session_id = session_id;
		this.session_name = sessionName;
		this.session_description = session_description;
		this.session_length = session_length;
	}

	public Session(String sessionName, String session_description, Integer session_length) {
		this.session_name = sessionName;
		this.session_description = session_description;
		this.session_length = session_length;
	}

	public Long getSession_id() {
		return session_id;
	}

	public void setSession_id(Long session_id) {
		this.session_id = session_id;
	}

	public List<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(List<Speaker> speakers) {
		this.speakers = speakers;
	}

	public String getSession_name() {
		return session_name;
	}

	public void setSession_name(String session_name) {
		this.session_name = session_name;
	}

	public String getSession_description() {
		return session_description;
	}

	public void setSession_description(String session_description) {
		this.session_description = session_description;
	}

	public Integer getSession_length() {
		return session_length;
	}

	public void setSession_length(Integer session_length) {
		this.session_length = session_length;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (session_id == null) {
			if (other.session_id != null) {
				return false;
			}
		} else if (!session_id.equals(other.session_id)) {
			return false;
		}
		return true;
	}

}
