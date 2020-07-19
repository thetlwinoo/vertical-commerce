package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Townships;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Townships entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TownshipsRepository extends JpaRepository<Townships, Long>, JpaSpecificationExecutor<Townships> {
}
