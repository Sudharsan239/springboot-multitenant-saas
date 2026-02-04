package com.orgflow.springboot_multitenant_saas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

@Entity(name = "users")
public class User
{
	@Id
	@GeneratedValue
	private Long id;

	private String username;

	private String email;

	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	private Organization organization;

	private Instant createdAt;

	private Instant modifiedAt;

	public User()
	{
	}

	public User(Long id, String username, String email, String password, Role role, Organization organization, Instant createdAt, Instant modifiedAt)
	{
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.organization = organization;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public Organization getOrganization()
	{
		return organization;
	}

	public void setOrganization(Organization organization)
	{
		this.organization = organization;
	}

	public Instant getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt)
	{
		this.createdAt = createdAt;
	}

	public Instant getModifiedAt()
	{
		return modifiedAt;
	}

	public void setModifiedAt(Instant modifiedAt)
	{
		this.modifiedAt = modifiedAt;
	}
}
