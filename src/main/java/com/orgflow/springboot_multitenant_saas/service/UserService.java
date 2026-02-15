package com.orgflow.springboot_multitenant_saas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orgflow.springboot_multitenant_saas.exception.UserNotFoundException;
import com.orgflow.springboot_multitenant_saas.model.Organization;
import com.orgflow.springboot_multitenant_saas.model.Role;
import com.orgflow.springboot_multitenant_saas.model.User;
import com.orgflow.springboot_multitenant_saas.repository.UserRepository;

@Service
@Transactional
public class UserService
{
	private final UserRepository userRepository;
	private final OrganizationService organizationService;

	public UserService(UserRepository userRepository, OrganizationService organizationService)
	{
		this.userRepository = userRepository;
		this.organizationService = organizationService;
	}

	@Transactional(readOnly = true)
	public Page<User> getUsersByOrganization(Long organizationId, String username, String email, Role role, Pageable pageable)
	{
		return userRepository.findByOrganizationIdWithFilters(organizationId, username, email, role, pageable);
	}

	@Transactional(readOnly = true)
	public User getUserById(Long organizationId, Long userId)
	{
		return userRepository.findByIdAndOrganizationId(userId, organizationId)
			.orElseThrow(() -> new UserNotFoundException(userId));
	}

	public User createUser(Long organizationId, String username, String email, String password, Role role)
	{
		Organization organization = organizationService.getOrganizationById(organizationId);
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setRole(role);
		user.setOrganization(organization);
		return userRepository.save(user);
	}

	public User updateUser(Long organizationId, Long userId, String username, String email, String password, Role role)
	{
		User user = getUserById(organizationId, userId);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setRole(role);
		return userRepository.save(user);
	}

	public void deleteUser(Long organizationId, Long userId)
	{
		User user = getUserById(organizationId, userId);
		userRepository.delete(user);
	}
}
