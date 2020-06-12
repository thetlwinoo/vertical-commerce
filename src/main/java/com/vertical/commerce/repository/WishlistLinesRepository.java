package com.vertical.commerce.repository;

import com.vertical.commerce.domain.WishlistLines;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WishlistLines entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WishlistLinesRepository extends JpaRepository<WishlistLines, Long>, JpaSpecificationExecutor<WishlistLines> {
}
