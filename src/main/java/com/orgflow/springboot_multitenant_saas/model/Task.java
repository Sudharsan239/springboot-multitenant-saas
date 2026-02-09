package com.orgflow.springboot_multitenant_saas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tasks")
public class Task
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Enumerated(EnumType.STRING)
	private TaskStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	private User assignedTo;

	@ManyToOne(fetch = FetchType.LAZY)
	private Project project;

	@ManyToOne(fetch = FetchType.LAZY)
	private Organization organization;

	public Task()
	{
	}

	public Task(Long id, String title, TaskStatus status, User assignedTo, Project project, Organization organization)
	{
		this.id = id;
		this.title = title;
		this.status = status;
		this.assignedTo = assignedTo;
		this.project = project;
		this.organization = organization;
	}

	public Long getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public TaskStatus getStatus()
	{
		return status;
	}

	public User getAssignedTo()
	{
		return assignedTo;
	}

	public Project getProject()
	{
		return project;
	}

	public Organization getOrganization()
	{
		return organization;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setStatus(TaskStatus status)
	{
		this.status = status;
	}

	public void setAssignedTo(User assignedTo)
	{
		this.assignedTo = assignedTo;
	}

	public void setProject(Project project)
	{
		this.project = project;
	}

	public void setOrganization(Organization organization)
	{
		this.organization = organization;
	}
}
