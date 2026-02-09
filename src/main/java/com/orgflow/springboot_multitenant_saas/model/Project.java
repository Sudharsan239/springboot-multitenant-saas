package com.orgflow.springboot_multitenant_saas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "projects")
public class Project
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public Organization getOrganization()
	{
		return organization;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setOrganization(Organization organization)
	{
		this.organization = organization;
	}
}
