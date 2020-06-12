package com.vertical.commerce.repository;

import com.vertical.commerce.domain.PackageTypes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PackageTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PackageTypesRepository extends JpaRepository<PackageTypes, Long>, JpaSpecificationExecutor<PackageTypes> {
}
