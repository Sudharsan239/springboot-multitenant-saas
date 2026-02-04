package com.orgflow.springboot_multitenant_saas.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orgflow.springboot_multitenant_saas.exception.OrganizationNotFoundException;
import com.orgflow.springboot_multitenant_saas.model.Organization;
import com.orgflow.springboot_multitenant_saas.repository.OrganizationRepository;

@Service
@Transactional
public class OrganizationService
{

	private final OrganizationRepository organizationRepository;

	public OrganizationService(OrganizationRepository organizationRepository)
	{
		this.organizationRepository = organizationRepository;
	}

	public List<Organization> getAllOrganizations()
	{
		return organizationRepository.findAll();
	}

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
		Organization org = getOrganizationById(id);
		organizationRepository.delete(org);
	}
}
