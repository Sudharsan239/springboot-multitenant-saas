package com.orgflow.springboot_multitenant_saas.controller;

import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.orgflow.springboot_multitenant_saas.dto.requestdto.CreateOrUpdateTaskRequest;
import com.orgflow.springboot_multitenant_saas.dto.responsedto.TaskResponse;
import com.orgflow.springboot_multitenant_saas.model.Task;
import com.orgflow.springboot_multitenant_saas.model.TaskStatus;
import com.orgflow.springboot_multitenant_saas.service.TaskService;

import jakarta.validation.Valid;

@RestController
public class TaskController
{
	private final TaskService taskService;

	public TaskController(TaskService taskService)
	{
		this.taskService = taskService;
	}

	@GetMapping("/organizations/{organizationId}/tasks")
	public Page<TaskResponse> getTasks(@PathVariable Long organizationId, @RequestParam(required = false) TaskStatus status,
		@RequestParam(required = false) Long projectId, @RequestParam(required = false) Long assignedToId,
		@RequestParam(required = false) String title, Pageable pageable)
	{
		return taskService.getTasksByOrganization(organizationId, status, projectId, assignedToId, title, pageable)
			.map(this::toResponse);
	}

	@GetMapping("/organizations/{organizationId}/tasks/{taskId}")
	public TaskResponse getTask(@PathVariable Long organizationId, @PathVariable Long taskId)
	{
		return toResponse(taskService.getTaskById(organizationId, taskId));
	}

	@PostMapping("/organizations/{organizationId}/tasks")
	public ResponseEntity<TaskResponse> createTask(@PathVariable Long organizationId,
		@Valid @RequestBody CreateOrUpdateTaskRequest request)
	{
		Task task = taskService.createTask(organizationId, request.getTitle(), request.getStatus(), request.getProjectId(),
			request.getAssignedToId());
		TaskResponse response = toResponse(task);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.getId()).toUri();
		return ResponseEntity.created(location).body(response);
	}

	@PutMapping("/organizations/{organizationId}/tasks/{taskId}")
	public ResponseEntity<TaskResponse> updateTask(@PathVariable Long organizationId, @PathVariable Long taskId,
		@Valid @RequestBody CreateOrUpdateTaskRequest request)
	{
		Task task = taskService.updateTask(organizationId, taskId, request.getTitle(), request.getStatus(),
			request.getProjectId(), request.getAssignedToId());
		return ResponseEntity.ok(toResponse(task));
	}

	@DeleteMapping("/organizations/{organizationId}/tasks/{taskId}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long organizationId, @PathVariable Long taskId)
	{
		taskService.deleteTask(organizationId, taskId);
		return ResponseEntity.noContent().build();
	}

	private TaskResponse toResponse(Task task)
	{
		Long assignedToId = task.getAssignedTo() == null ? null : task.getAssignedTo().getId();
		return new TaskResponse(task.getId(), task.getTitle(), task.getStatus(), assignedToId, task.getProject().getId(),
			task.getOrganization().getId());
	}
}
