package com.orgflow.springboot_multitenant_saas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orgflow.springboot_multitenant_saas.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long>
{

}
