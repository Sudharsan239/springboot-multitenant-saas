package com.orgflow.springboot_multitenant_saas.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateOrUpdateProjectRequest
{
	@NotBlank(message = "Project name must not be blank")
	@Size(min = 3, max = 100, message = "Project name must be between 3 and 100 characters long")
	private String name;

	public CreateOrUpdateProjectRequest()
	{
	}

	public CreateOrUpdateProjectRequest(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
