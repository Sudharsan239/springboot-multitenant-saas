package com.orgflow.springboot_multitenant_saas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "projects")
@Getter
@Setter
public class Project
{
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne
	private Organization organization;

	public Project()
	{
	}

	public Project(Long id, String name, Organization organization)
	{
		this.id = id;
		this.name = name;
		this.organization = organization;
	}

}
