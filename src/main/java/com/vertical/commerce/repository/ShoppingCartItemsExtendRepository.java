package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ShoppingCartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoppingCartItemsExtendRepository extends JpaRepository<ShoppingCartItems,Long> {
    @Query(value = "SELECT p.supplier_id FROM shopping_cart_items sci INNER JOIN stock_items si ON sci.stock_item_id = si.id INNER JOIN products p ON si.product_id = p.id WHERE sci.cart_id = :cartId GROUP BY p.supplier_id", nativeQuery = true)
    List<Long> getSupplierListByCart(@Param("cartId") Long cartId);

    void deleteAllByIdIn(List<Long> idList);

    @Modifying
    @Query(value = "DELETE FROM shopping_cart_items WHERE id IN :idList", nativeQuery = true)
    void deleteSomeCartItems(@Param("idList") List<Long> idList);
}
