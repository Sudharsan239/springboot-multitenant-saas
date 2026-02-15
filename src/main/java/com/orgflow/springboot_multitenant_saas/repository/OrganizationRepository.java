package com.orgflow.springboot_multitenant_saas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.orgflow.springboot_multitenant_saas.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long>
{
	@Query("""
		select o from organizations o
		where (:name is null or lower(o.name) like lower(concat('%', :name, '%')))
		""")
	Page<Organization> findAllByNameFilter(@Param("name") String name, Pageable pageable);
}
