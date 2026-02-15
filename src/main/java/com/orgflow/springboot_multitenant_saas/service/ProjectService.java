package com.orgflow.springboot_multitenant_saas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orgflow.springboot_multitenant_saas.exception.ProjectNotFoundException;
import com.orgflow.springboot_multitenant_saas.model.Organization;
import com.orgflow.springboot_multitenant_saas.model.Project;
import com.orgflow.springboot_multitenant_saas.repository.ProjectRepository;

@Service
@Transactional
public class ProjectService
{
	private final ProjectRepository projectRepository;
	private final OrganizationService organizationService;

	public ProjectService(ProjectRepository projectRepository, OrganizationService organizationService)
	{
		this.projectRepository = projectRepository;
		this.organizationService = organizationService;
	}

	@Transactional(readOnly = true)
	public Page<Project> getProjectsByOrganization(Long organizationId, String name, Pageable pageable)
	{
		return projectRepository.findByOrganizationIdWithFilters(organizationId, name, pageable);
	}

	@Transactional(readOnly = true)
	public Project getProjectById(Long organizationId, Long projectId)
	{
		return projectRepository.findByIdAndOrganizationId(projectId, organizationId)
			.orElseThrow(() -> new ProjectNotFoundException(projectId));
	}

	public Project createProject(Long organizationId, String name)
	{
		Organization organization = organizationService.getOrganizationById(organizationId);
		Project project = new Project();
		project.setName(name);
		project.setOrganization(organization);
		return projectRepository.save(project);
	}

	public Project updateProject(Long organizationId, Long projectId, String name)
	{
		Project project = getProjectById(organizationId, projectId);
		project.setName(name);
		return projectRepository.save(project);
	}

	public void deleteProject(Long organizationId, Long projectId)
	{
		Project project = getProjectById(organizationId, projectId);
		projectRepository.delete(project);
	}
}
