package com.orgflow.springboot_multitenant_saas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orgflow.springboot_multitenant_saas.exception.OrganizationNotFoundException;
import com.orgflow.springboot_multitenant_saas.model.Organization;
import com.orgflow.springboot_multitenant_saas.repository.OrganizationRepository;
import com.orgflow.springboot_multitenant_saas.repository.ProjectRepository;
import com.orgflow.springboot_multitenant_saas.repository.TaskRepository;
import com.orgflow.springboot_multitenant_saas.repository.UserRepository;

@Service
@Transactional
public class OrganizationService
{

	private final OrganizationRepository organizationRepository;
	private final UserRepository userRepository;
	private final ProjectRepository projectRepository;
	private final TaskRepository taskRepository;

	public OrganizationService(OrganizationRepository organizationRepository, UserRepository userRepository,
		ProjectRepository projectRepository, TaskRepository taskRepository)
	{
		this.organizationRepository = organizationRepository;
		this.userRepository = userRepository;
		this.projectRepository = projectRepository;
		this.taskRepository = taskRepository;
	}

	@Transactional(readOnly = true)
	public Page<Organization> getAllOrganizations(String name, Pageable pageable)
	{
		return organizationRepository.findAllByNameFilter(name, pageable);
	}

	@Transactional(readOnly = true)
	public Organization getOrganizationById(Long id)
	{
		return organizationRepository.findById(id)
			.orElseThrow(() -> new OrganizationNotFoundException(id));
	}

	public Organization createOrganization(String name)
	{
		Organization org = new Organization();
		org.setName(name);
		return organizationRepository.save(org);
	}

	public Organization updateOrganization(Long id, String name)
	{
		Organization org = getOrganizationById(id);
		org.setName(name);
		return organizationRepository.save(org);
	}

	public void deleteOrganization(Long id)
	{
		getOrganizationById(id);

		// Delete children first to satisfy foreign key constraints.
		taskRepository.deleteByOrganizationId(id);
		projectRepository.deleteByOrganizationId(id);
		userRepository.deleteByOrganizationId(id);
		organizationRepository.deleteById(id);
	}
}
