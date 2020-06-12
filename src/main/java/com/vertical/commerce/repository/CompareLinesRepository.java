package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CompareLines;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompareLines entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompareLinesRepository extends JpaRepository<CompareLines, Long>, JpaSpecificationExecutor<CompareLines> {
}
