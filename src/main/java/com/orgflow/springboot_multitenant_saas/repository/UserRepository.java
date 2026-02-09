package com.orgflow.springboot_multitenant_saas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orgflow.springboot_multitenant_saas.model.User;

public interface UserRepository extends JpaRepository<User, Long>
{
	List<User> findByOrganizationId(Long organizationId);

	Optional<User> findByIdAndOrganizationId(Long id, Long organizationId);
}
