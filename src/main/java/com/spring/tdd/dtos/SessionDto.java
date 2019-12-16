package com.spring.tdd.dtos;

public class SessionDto {
	private Long id;
	private String name;
	private String description;
	private Integer length;

	public SessionDto(Long id, String name, String description, Integer length) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.length = length;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Integer getLength() {
		return length;
	}

}
