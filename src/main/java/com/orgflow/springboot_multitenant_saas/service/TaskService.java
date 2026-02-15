package com.orgflow.springboot_multitenant_saas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orgflow.springboot_multitenant_saas.exception.ProjectNotFoundException;
import com.orgflow.springboot_multitenant_saas.exception.TaskNotFoundException;
import com.orgflow.springboot_multitenant_saas.exception.UserNotFoundException;
import com.orgflow.springboot_multitenant_saas.model.Organization;
import com.orgflow.springboot_multitenant_saas.model.Project;
import com.orgflow.springboot_multitenant_saas.model.Task;
import com.orgflow.springboot_multitenant_saas.model.TaskStatus;
import com.orgflow.springboot_multitenant_saas.model.User;
import com.orgflow.springboot_multitenant_saas.repository.ProjectRepository;
import com.orgflow.springboot_multitenant_saas.repository.TaskRepository;
import com.orgflow.springboot_multitenant_saas.repository.UserRepository;

@Service
@Transactional
public class TaskService
{
	private final TaskRepository taskRepository;
	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;
	private final OrganizationService organizationService;

	public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository,
		OrganizationService organizationService)
	{
		this.taskRepository = taskRepository;
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
		this.organizationService = organizationService;
	}

	@Transactional(readOnly = true)
	public Page<Task> getTasksByOrganization(Long organizationId, TaskStatus status, Long projectId, Long assignedToId,
		String title, Pageable pageable)
	{
		return taskRepository.findByOrganizationIdWithFilters(organizationId, status, projectId, assignedToId, title, pageable);
	}

	@Transactional(readOnly = true)
	public Task getTaskById(Long organizationId, Long taskId)
	{
		return taskRepository.findByIdAndOrganizationId(taskId, organizationId)
			.orElseThrow(() -> new TaskNotFoundException(taskId));
	}

	public Task createTask(Long organizationId, String title, TaskStatus status, Long projectId, Long assignedToId)
	{
		Organization organization = organizationService.getOrganizationById(organizationId);
		Project project = projectRepository.findByIdAndOrganizationId(projectId, organizationId)
			.orElseThrow(() -> new ProjectNotFoundException(projectId));
		User assignedTo = resolveAssignedUser(organizationId, assignedToId);

		Task task = new Task();
		task.setTitle(title);
		task.setStatus(status);
		task.setProject(project);
		task.setAssignedTo(assignedTo);
		task.setOrganization(organization);
		return taskRepository.save(task);
	}

	public Task updateTask(Long organizationId, Long taskId, String title, TaskStatus status, Long projectId, Long assignedToId)
	{
		Task task = getTaskById(organizationId, taskId);
		Project project = projectRepository.findByIdAndOrganizationId(projectId, organizationId)
			.orElseThrow(() -> new ProjectNotFoundException(projectId));
		User assignedTo = resolveAssignedUser(organizationId, assignedToId);

		task.setTitle(title);
		task.setStatus(status);
		task.setProject(project);
		task.setAssignedTo(assignedTo);
		return taskRepository.save(task);
	}

	public void deleteTask(Long organizationId, Long taskId)
	{
		Task task = getTaskById(organizationId, taskId);
		taskRepository.delete(task);
	}

	private User resolveAssignedUser(Long organizationId, Long assignedToId)
	{
		if (assignedToId == null)
		{
			return null;
		}
		return userRepository.findByIdAndOrganizationId(assignedToId, organizationId)
			.orElseThrow(() -> new UserNotFoundException(assignedToId));
	}
}
