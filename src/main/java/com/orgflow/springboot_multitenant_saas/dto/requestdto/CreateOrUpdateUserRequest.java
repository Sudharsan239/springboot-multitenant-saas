package com.orgflow.springboot_multitenant_saas.dto.requestdto;

import com.orgflow.springboot_multitenant_saas.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateOrUpdateUserRequest
{
	@NotBlank(message = "Username must not be blank")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters long")
	private String username;

	@NotBlank(message = "Email must not be blank")
	@Email(message = "Email must be valid")
	private String email;

	@NotBlank(message = "Password must not be blank")
	@Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters long")
	private String password;

	@NotNull(message = "Role must be provided")
	private Role role;

	public CreateOrUpdateUserRequest()
	{
	}

	public CreateOrUpdateUserRequest(String username, String email, String password, Role role)
	{
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
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
}
