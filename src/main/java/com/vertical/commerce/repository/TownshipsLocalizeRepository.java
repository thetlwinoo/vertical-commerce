package com.vertical.commerce.repository;

import com.vertical.commerce.domain.TownshipsLocalize;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TownshipsLocalize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TownshipsLocalizeRepository extends JpaRepository<TownshipsLocalize, Long>, JpaSpecificationExecutor<TownshipsLocalize> {
}
