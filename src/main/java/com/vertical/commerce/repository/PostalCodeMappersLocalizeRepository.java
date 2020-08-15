package com.vertical.commerce.repository;

import com.vertical.commerce.domain.PostalCodeMappersLocalize;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PostalCodeMappersLocalize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostalCodeMappersLocalizeRepository extends JpaRepository<PostalCodeMappersLocalize, Long>, JpaSpecificationExecutor<PostalCodeMappersLocalize> {
}
