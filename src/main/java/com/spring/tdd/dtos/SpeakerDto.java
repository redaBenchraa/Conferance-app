package com.spring.tdd.dtos;

public class SpeakerDto {
	private Long id;
	private String name;
	private String familyName;
	private String job;
	private String companyName;
	private String bio;

	public SpeakerDto(Long id, String name, String familyName, String job, String companyName, String bio) {
		this.id = id;
		this.name = name;
		this.familyName = familyName;
		this.job = job;
		this.companyName = companyName;
		this.bio = bio;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getJob() {
		return job;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getBio() {
		return bio;
	}

}
