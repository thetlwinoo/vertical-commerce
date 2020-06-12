package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ShoppingCarts;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ShoppingCarts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingCartsRepository extends JpaRepository<ShoppingCarts, Long>, JpaSpecificationExecutor<ShoppingCarts> {
}
