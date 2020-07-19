package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Districts;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Districts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistrictsRepository extends JpaRepository<Districts, Long>, JpaSpecificationExecutor<Districts> {
}
