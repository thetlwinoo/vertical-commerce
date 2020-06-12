package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ShoppingCartItems;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ShoppingCartItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingCartItemsRepository extends JpaRepository<ShoppingCartItems, Long>, JpaSpecificationExecutor<ShoppingCartItems> {
}
