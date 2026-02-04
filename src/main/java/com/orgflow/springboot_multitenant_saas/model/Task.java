package com.orgflow.springboot_multitenant_saas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tasks")
@Getter
@Setter
public class Task
{
	@Id
	@GeneratedValue
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
}
