package com.orgflow.springboot_multitenant_saas.dto.responsedto;

import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class OrganizationResponse
{
	private Long id;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public OrganizationResponse(Long id, String name, Instant createdAt, Instant modifiedAt)
	{
		this.id = id;
		this.name = name;
		this.createdAt = LocalDateTime.ofInstant(createdAt, ZoneId.systemDefault());
		this.modifiedAt = LocalDateTime.ofInstant(modifiedAt, ZoneId.systemDefault());
	}

}
