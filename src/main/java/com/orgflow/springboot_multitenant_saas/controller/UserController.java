package com.orgflow.springboot_multitenant_saas.controller;

import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.orgflow.springboot_multitenant_saas.dto.requestdto.CreateOrUpdateUserRequest;
import com.orgflow.springboot_multitenant_saas.dto.responsedto.UserResponse;
import com.orgflow.springboot_multitenant_saas.model.Role;
import com.orgflow.springboot_multitenant_saas.model.User;
import com.orgflow.springboot_multitenant_saas.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController
{
	private final UserService userService;

	public UserController(UserService userService)
	{
		this.userService = userService;
	}

	@GetMapping("/organizations/{organizationId}/users")
	public Page<UserResponse> getUsers(@PathVariable Long organizationId, @RequestParam(required = false) String username,
		@RequestParam(required = false) String email, @RequestParam(required = false) Role role, Pageable pageable)
	{
		return userService.getUsersByOrganization(organizationId, username, email, role, pageable)
			.map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(),
				user.getOrganization().getId(), user.getCreatedAt(), user.getModifiedAt()));
	}

	@GetMapping("/organizations/{organizationId}/users/{userId}")
	public UserResponse getUser(@PathVariable Long organizationId, @PathVariable Long userId)
	{
		User user = userService.getUserById(organizationId, userId);
		return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(),
			user.getOrganization().getId(), user.getCreatedAt(), user.getModifiedAt());
	}

	@PostMapping("/organizations/{organizationId}/users")
	public ResponseEntity<UserResponse> createUser(@PathVariable Long organizationId,
		@Valid @RequestBody CreateOrUpdateUserRequest request)
	{
		User user = userService.createUser(organizationId, request.getUsername(), request.getEmail(), request.getPassword(),
			request.getRole());
		UserResponse response = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(),
			user.getOrganization().getId(), user.getCreatedAt(), user.getModifiedAt());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(location).body(response);
	}

	@PutMapping("/organizations/{organizationId}/users/{userId}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable Long organizationId, @PathVariable Long userId,
		@Valid @RequestBody CreateOrUpdateUserRequest request)
	{
		User user = userService.updateUser(organizationId, userId, request.getUsername(), request.getEmail(),
			request.getPassword(), request.getRole());
		UserResponse response = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(),
			user.getOrganization().getId(), user.getCreatedAt(), user.getModifiedAt());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/organizations/{organizationId}/users/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long organizationId, @PathVariable Long userId)
	{
		userService.deleteUser(organizationId, userId);
		return ResponseEntity.noContent().build();
	}
}
