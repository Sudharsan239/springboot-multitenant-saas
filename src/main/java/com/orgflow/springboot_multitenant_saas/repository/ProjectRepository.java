package com.orgflow.springboot_multitenant_saas.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.orgflow.springboot_multitenant_saas.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>
{
	@Modifying(clearAutomatically = true)
	@Query("delete from projects p where p.organization.id = :organizationId")
	void deleteByOrganizationId(@Param("organizationId") Long organizationId);

	@Query("""
		select p from projects p
		where p.organization.id = :organizationId
			and (:name is null or lower(p.name) like lower(concat('%', :name, '%')))
		""")
	Page<Project> findByOrganizationIdWithFilters(@Param("organizationId") Long organizationId,
		@Param("name") String name, Pageable pageable);

	Optional<Project> findByIdAndOrganizationId(Long id, Long organizationId);
}
