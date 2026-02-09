package com.orgflow.springboot_multitenant_saas.dto.requestdto;

import com.orgflow.springboot_multitenant_saas.model.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateOrUpdateTaskRequest
{
	@NotBlank(message = "Task title must not be blank")
	@Size(min = 3, max = 150, message = "Task title must be between 3 and 150 characters long")
	private String title;

	@NotNull(message = "Task status must be provided")
	private TaskStatus status;

	@NotNull(message = "Project id must be provided")
	private Long projectId;

	private Long assignedToId;

	public CreateOrUpdateTaskRequest()
	{
	}

	public CreateOrUpdateTaskRequest(String title, TaskStatus status, Long projectId, Long assignedToId)
	{
		this.title = title;
		this.status = status;
		this.projectId = projectId;
		this.assignedToId = assignedToId;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public TaskStatus getStatus()
	{
		return status;
	}

	public void setStatus(TaskStatus status)
	{
		this.status = status;
	}

	public Long getProjectId()
	{
		return projectId;
	}

	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}

	public Long getAssignedToId()
	{
		return assignedToId;
	}

	public void setAssignedToId(Long assignedToId)
	{
		this.assignedToId = assignedToId;
	}
}
