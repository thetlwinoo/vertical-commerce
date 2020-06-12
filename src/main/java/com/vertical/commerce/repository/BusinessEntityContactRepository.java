package com.vertical.commerce.repository;

import com.vertical.commerce.domain.BusinessEntityContact;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BusinessEntityContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessEntityContactRepository extends JpaRepository<BusinessEntityContact, Long>, JpaSpecificationExecutor<BusinessEntityContact> {
}
