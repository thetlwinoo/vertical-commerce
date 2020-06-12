package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CardTypeCreditCards;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CardTypeCreditCards entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardTypeCreditCardsRepository extends JpaRepository<CardTypeCreditCards, Long>, JpaSpecificationExecutor<CardTypeCreditCards> {
}
