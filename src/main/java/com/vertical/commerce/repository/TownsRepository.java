package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Towns;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Towns entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TownsRepository extends JpaRepository<Towns, Long>, JpaSpecificationExecutor<Towns> {
}
