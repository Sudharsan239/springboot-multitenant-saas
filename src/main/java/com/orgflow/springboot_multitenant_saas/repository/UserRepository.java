package com.orgflow.springboot_multitenant_saas.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.orgflow.springboot_multitenant_saas.model.Role;
import com.orgflow.springboot_multitenant_saas.model.User;

public interface UserRepository extends JpaRepository<User, Long>
{
	@Query("""
		select u from users u
		where u.organization.id = :organizationId
			and (:username is null or lower(u.username) like lower(concat('%', :username, '%')))
			and (:email is null or lower(u.email) like lower(concat('%', :email, '%')))
			and (:role is null or u.role = :role)
		""")
	Page<User> findByOrganizationIdWithFilters(@Param("organizationId") Long organizationId,
		@Param("username") String username, @Param("email") String email, @Param("role") Role role, Pageable pageable);

	Optional<User> findByIdAndOrganizationId(Long id, Long organizationId);
}
