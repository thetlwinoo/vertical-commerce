package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Cards;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cards entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardsRepository extends JpaRepository<Cards, Long>, JpaSpecificationExecutor<Cards> {
}
