package com.orgflow.springboot_multitenant_saas.dto.responsedto;

import com.orgflow.springboot_multitenant_saas.model.TaskStatus;

public class TaskResponse
{
	private Long id;
	private String title;
	private TaskStatus status;
	private Long assignedToId;
	private Long projectId;
	private Long organizationId;

	public TaskResponse(Long id, String title, TaskStatus status, Long assignedToId, Long projectId, Long organizationId)
	{
		this.id = id;
		this.title = title;
		this.status = status;
		this.assignedToId = assignedToId;
		this.projectId = projectId;
		this.organizationId = organizationId;
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

	public Long getAssignedToId()
	{
		return assignedToId;
	}

	public Long getProjectId()
	{
		return projectId;
	}

	public Long getOrganizationId()
	{
		return organizationId;
	}
}
