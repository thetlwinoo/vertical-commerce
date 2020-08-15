package com.vertical.commerce.repository;

import com.vertical.commerce.domain.RegionsLocalize;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RegionsLocalize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionsLocalizeRepository extends JpaRepository<RegionsLocalize, Long>, JpaSpecificationExecutor<RegionsLocalize> {
}
