package com.orgflow.springboot_multitenant_saas.dto.responsedto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.orgflow.springboot_multitenant_saas.model.Role;

public class UserResponse
{
	private Long id;
	private String username;
	private String email;
	private Role role;
	private Long organizationId;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public UserResponse(Long id, String username, String email, Role role, Long organizationId, Instant createdAt, Instant modifiedAt)
	{
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
		this.organizationId = organizationId;
		this.createdAt = createdAt == null ? null : LocalDateTime.ofInstant(createdAt, ZoneId.systemDefault());
		this.modifiedAt = modifiedAt == null ? null : LocalDateTime.ofInstant(modifiedAt, ZoneId.systemDefault());
	}

	public Long getId()
	{
		return id;
	}

	public String getUsername()
	{
		return username;
	}

	public String getEmail()
	{
		return email;
	}

	public Role getRole()
	{
		return role;
	}

	public Long getOrganizationId()
	{
		return organizationId;
	}

	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	public LocalDateTime getModifiedAt()
	{
		return modifiedAt;
	}
}
