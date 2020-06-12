package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Materials;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Materials entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialsRepository extends JpaRepository<Materials, Long>, JpaSpecificationExecutor<Materials> {
}
