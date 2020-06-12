package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CardTypes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CardTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardTypesRepository extends JpaRepository<CardTypes, Long>, JpaSpecificationExecutor<CardTypes> {
}
