package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ShoppingCarts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingCartsExtendRepository extends JpaRepository<ShoppingCarts,Long> {
    @Query(value = "SELECT * FROM cart_details(:shoppingCartId)", nativeQuery = true)
    String getCartDetails(@Param("shoppingCartId") Long shoppingCartId);
}
