package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductDocuments;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductDocuments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductDocumentsRepository extends JpaRepository<ProductDocuments, Long>, JpaSpecificationExecutor<ProductDocuments> {
}
