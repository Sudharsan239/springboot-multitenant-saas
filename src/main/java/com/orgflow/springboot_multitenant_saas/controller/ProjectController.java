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

import com.orgflow.springboot_multitenant_saas.dto.requestdto.CreateOrUpdateProjectRequest;
import com.orgflow.springboot_multitenant_saas.dto.responsedto.ProjectResponse;
import com.orgflow.springboot_multitenant_saas.model.Project;
import com.orgflow.springboot_multitenant_saas.service.ProjectService;

import jakarta.validation.Valid;

@RestController
public class ProjectController
{
	private final ProjectService projectService;

	public ProjectController(ProjectService projectService)
	{
		this.projectService = projectService;
	}

	@GetMapping("/organizations/{organizationId}/projects")
	public Page<ProjectResponse> getProjects(@PathVariable Long organizationId, @RequestParam(required = false) String name,
		Pageable pageable)
	{
		return projectService.getProjectsByOrganization(organizationId, name, pageable)
			.map(project -> new ProjectResponse(project.getId(), project.getName(), project.getOrganization().getId()));
	}

	@GetMapping("/organizations/{organizationId}/projects/{projectId}")
	public ProjectResponse getProject(@PathVariable Long organizationId, @PathVariable Long projectId)
	{
		Project project = projectService.getProjectById(organizationId, projectId);
		return new ProjectResponse(project.getId(), project.getName(), project.getOrganization().getId());
	}

	@PostMapping("/organizations/{organizationId}/projects")
	public ResponseEntity<ProjectResponse> createProject(@PathVariable Long organizationId,
		@Valid @RequestBody CreateOrUpdateProjectRequest request)
	{
		Project project = projectService.createProject(organizationId, request.getName());
		ProjectResponse response = new ProjectResponse(project.getId(), project.getName(), project.getOrganization().getId());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(project.getId()).toUri();
		return ResponseEntity.created(location).body(response);
	}

	@PutMapping("/organizations/{organizationId}/projects/{projectId}")
	public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long organizationId, @PathVariable Long projectId,
		@Valid @RequestBody CreateOrUpdateProjectRequest request)
	{
		Project project = projectService.updateProject(organizationId, projectId, request.getName());
		ProjectResponse response = new ProjectResponse(project.getId(), project.getName(), project.getOrganization().getId());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/organizations/{organizationId}/projects/{projectId}")
	public ResponseEntity<Void> deleteProject(@PathVariable Long organizationId, @PathVariable Long projectId)
	{
		projectService.deleteProject(organizationId, projectId);
		return ResponseEntity.noContent().build();
	}
}
