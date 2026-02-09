package com.orgflow.springboot_multitenant_saas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orgflow.springboot_multitenant_saas.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>
{
	List<Project> findByOrganizationId(Long organizationId);

	Optional<Project> findByIdAndOrganizationId(Long id, Long organizationId);
}
