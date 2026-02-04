package com.orgflow.springboot_multitenant_saas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrganizationNotFoundException extends RuntimeException
{
	public OrganizationNotFoundException(Long id)
	{
		super("Organization not found with id: " + id);
	}
}
