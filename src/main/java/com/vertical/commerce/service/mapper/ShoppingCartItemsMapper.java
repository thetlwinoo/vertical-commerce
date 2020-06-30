package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ShoppingCartItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShoppingCartItems} and its DTO {@link ShoppingCartItemsDTO}.
 */
@Mapper(componentModel = "spring", uses = {StockItemsMapper.class, DeliveryMethodsMapper.class, ShoppingCartsMapper.class})
public interface ShoppingCartItemsMapper extends EntityMapper<ShoppingCartItemsDTO, ShoppingCartItems> {

    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "stockItem.name", target = "stockItemName")
    @Mapping(source = "deliveryMethod.id", target = "deliveryMethodId")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethodName")
    @Mapping(source = "cart.id", target = "cartId")
    ShoppingCartItemsDTO toDto(ShoppingCartItems shoppingCartItems);

    @Mapping(source = "stockItemId", target = "stockItem")
    @Mapping(source = "deliveryMethodId", target = "deliveryMethod")
    @Mapping(source = "cartId", target = "cart")
    ShoppingCartItems toEntity(ShoppingCartItemsDTO shoppingCartItemsDTO);

    default ShoppingCartItems fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShoppingCartItems shoppingCartItems = new ShoppingCartItems();
        shoppingCartItems.setId(id);
        return shoppingCartItems;
    }
}
