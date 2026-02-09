package com.orgflow.springboot_multitenant_saas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orgflow.springboot_multitenant_saas.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>
{
	List<Task> findByOrganizationId(Long organizationId);

	Optional<Task> findByIdAndOrganizationId(Long id, Long organizationId);
}
