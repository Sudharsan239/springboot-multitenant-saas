package com.orgflow.springboot_multitenant_saas.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.orgflow.springboot_multitenant_saas.model.TaskStatus;
import com.orgflow.springboot_multitenant_saas.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>
{
	@Query("""
		select t from tasks t
		where t.organization.id = :organizationId
			and (:status is null or t.status = :status)
			and (:projectId is null or t.project.id = :projectId)
			and (:assignedToId is null or (t.assignedTo is not null and t.assignedTo.id = :assignedToId))
			and (:title is null or lower(t.title) like lower(concat('%', :title, '%')))
		""")
	Page<Task> findByOrganizationIdWithFilters(@Param("organizationId") Long organizationId,
		@Param("status") TaskStatus status, @Param("projectId") Long projectId,
		@Param("assignedToId") Long assignedToId, @Param("title") String title, Pageable pageable);

	Optional<Task> findByIdAndOrganizationId(Long id, Long organizationId);
}
