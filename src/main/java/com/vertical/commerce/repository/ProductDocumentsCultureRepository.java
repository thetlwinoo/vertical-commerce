package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductDocumentsCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductDocumentsCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductDocumentsCultureRepository extends JpaRepository<ProductDocumentsCulture, Long>, JpaSpecificationExecutor<ProductDocumentsCulture> {
}
