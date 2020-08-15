package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.StockItemsLocalizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StockItemsLocalize} and its DTO {@link StockItemsLocalizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, StockItemsMapper.class})
public interface StockItemsLocalizeMapper extends EntityMapper<StockItemsLocalizeDTO, StockItemsLocalize> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "stockItem.name", target = "stockItemName")
    StockItemsLocalizeDTO toDto(StockItemsLocalize stockItemsLocalize);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "stockItemId", target = "stockItem")
    StockItemsLocalize toEntity(StockItemsLocalizeDTO stockItemsLocalizeDTO);

    default StockItemsLocalize fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockItemsLocalize stockItemsLocalize = new StockItemsLocalize();
        stockItemsLocalize.setId(id);
        return stockItemsLocalize;
    }
}
