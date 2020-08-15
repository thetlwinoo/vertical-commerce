package com.vertical.commerce.repository;

import com.vertical.commerce.domain.PostalCodeMappers;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PostalCodeMappers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostalCodeMappersRepository extends JpaRepository<PostalCodeMappers, Long>, JpaSpecificationExecutor<PostalCodeMappers> {
}
