package com.vertical.commerce.repository;

import com.vertical.commerce.domain.TransactionTypes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransactionTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionTypesRepository extends JpaRepository<TransactionTypes, Long>, JpaSpecificationExecutor<TransactionTypes> {
}
