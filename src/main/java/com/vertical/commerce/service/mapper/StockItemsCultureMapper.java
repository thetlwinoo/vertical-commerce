package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.StockItemsCultureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StockItemsCulture} and its DTO {@link StockItemsCultureDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, StockItemsMapper.class})
public interface StockItemsCultureMapper extends EntityMapper<StockItemsCultureDTO, StockItemsCulture> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "stockItem.name", target = "stockItemName")
    StockItemsCultureDTO toDto(StockItemsCulture stockItemsCulture);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "stockItemId", target = "stockItem")
    StockItemsCulture toEntity(StockItemsCultureDTO stockItemsCultureDTO);

    default StockItemsCulture fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockItemsCulture stockItemsCulture = new StockItemsCulture();
        stockItemsCulture.setId(id);
        return stockItemsCulture;
    }
}
