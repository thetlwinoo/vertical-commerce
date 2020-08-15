package com.vertical.commerce.repository;

import com.vertical.commerce.domain.TownsLocalize;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TownsLocalize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TownsLocalizeRepository extends JpaRepository<TownsLocalize, Long>, JpaSpecificationExecutor<TownsLocalize> {
}
