package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.WishlistLinesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WishlistLines} and its DTO {@link WishlistLinesDTO}.
 */
@Mapper(componentModel = "spring", uses = {StockItemsMapper.class, WishlistsMapper.class})
public interface WishlistLinesMapper extends EntityMapper<WishlistLinesDTO, WishlistLines> {

    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "stockItem.name", target = "stockItemName")
    @Mapping(source = "wishlist.id", target = "wishlistId")
    WishlistLinesDTO toDto(WishlistLines wishlistLines);

    @Mapping(source = "stockItemId", target = "stockItem")
    @Mapping(source = "wishlistId", target = "wishlist")
    WishlistLines toEntity(WishlistLinesDTO wishlistLinesDTO);

    default WishlistLines fromId(Long id) {
        if (id == null) {
            return null;
        }
        WishlistLines wishlistLines = new WishlistLines();
        wishlistLines.setId(id);
        return wishlistLines;
    }
}
