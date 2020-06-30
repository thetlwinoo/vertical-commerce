package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "shopping_items")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShoppingItems {
    Long id;
    Long productId;
    String productName;
    Long stockItemId;
    String stockItemName;
    Boolean selectOrder;
    Integer quantity;
    BigDecimal unitPrice;
    Integer quantityOnHand;
    Long deliveryMethodId;
    Long blobId;
}
