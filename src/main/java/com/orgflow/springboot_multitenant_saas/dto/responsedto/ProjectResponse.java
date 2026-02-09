package com.orgflow.springboot_multitenant_saas.dto.responsedto;

public class ProjectResponse
{
	private Long id;
	private String name;
	private Long organizationId;

	public ProjectResponse(Long id, String name, Long organizationId)
	{
		this.id = id;
		this.name = name;
		this.organizationId = organizationId;
	}

	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public Long getOrganizationId()
	{
		return organizationId;
	}
}
