package com.vertical.commerce.repository;

import com.vertical.commerce.domain.WarrantyTypes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WarrantyTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WarrantyTypesRepository extends JpaRepository<WarrantyTypes, Long>, JpaSpecificationExecutor<WarrantyTypes> {
}
