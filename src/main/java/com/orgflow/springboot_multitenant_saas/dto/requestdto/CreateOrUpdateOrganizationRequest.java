package com.orgflow.springboot_multitenant_saas.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateOrUpdateOrganizationRequest
{
	@NotBlank(message = "Organization name must not be blank")
	@Size(min = 3, message = "Organization name must be at least 3 characters long")
	private String name;

	public String getName()
	{
		return name;
	}
}
