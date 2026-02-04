package com.orgflow.springboot_multitenant_saas.controller;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.orgflow.springboot_multitenant_saas.dto.requestdto.CreateOrUpdateOrganizationRequest;
import com.orgflow.springboot_multitenant_saas.dto.responsedto.OrganizationResponse;
import com.orgflow.springboot_multitenant_saas.model.Organization;
import com.orgflow.springboot_multitenant_saas.service.OrganizationService;

@RestController
public class OrganizationController
{

	private OrganizationService organizationService;

	public OrganizationController(OrganizationService organizationService)
	{
		this.organizationService = organizationService;
	}

	@GetMapping("/organizations")
	public List<OrganizationResponse> getOrganizations()
	{
		List<Organization> organizationList = organizationService.getAllOrganizations();
		List<OrganizationResponse> responseList = organizationList.stream()
			.map(org -> new OrganizationResponse(org.getId(), org.getName(), org.getCreatedAt(), org.getModifiedAt()))
			.toList();
		return responseList;
	}

	@GetMapping("/organizations/{id}")
	public OrganizationResponse getOrganizationById(@PathVariable Long id)
	{
		Organization organization = organizationService.getOrganizationById(id);
		OrganizationResponse response = new OrganizationResponse(organization.getId(), organization.getName(), organization.getCreatedAt(), organization.getModifiedAt());
		return response;
	}

	@PostMapping("/organizations")
	public ResponseEntity<OrganizationResponse> createOrganization(@Valid @RequestBody CreateOrUpdateOrganizationRequest request)
	{
		Organization savedOrg = organizationService.createOrganization(request.getName());
		OrganizationResponse response = new OrganizationResponse(savedOrg.getId(), savedOrg.getName(), savedOrg.getCreatedAt(), savedOrg.getModifiedAt());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedOrg.getId()).toUri();
		return ResponseEntity.created(location).body(response);
	}

	@PutMapping("/organizations/{id}")
	public ResponseEntity<OrganizationResponse> updateOrganization(@PathVariable Long id, @Valid @RequestBody CreateOrUpdateOrganizationRequest request)
	{
		Organization updatedOrg = organizationService.updateOrganization(id, request.getName());
		OrganizationResponse response = new OrganizationResponse(updatedOrg.getId(), updatedOrg.getName(), updatedOrg.getCreatedAt(), updatedOrg.getModifiedAt());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/organizations/{id}")
	public ResponseEntity<Void> deleteOrganization(@PathVariable Long id)
	{
		organizationService.deleteOrganization(id);
		return ResponseEntity.noContent().build();
	}
}
