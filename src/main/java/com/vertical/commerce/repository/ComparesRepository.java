package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Compares;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Compares entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComparesRepository extends JpaRepository<Compares, Long>, JpaSpecificationExecutor<Compares> {
}
