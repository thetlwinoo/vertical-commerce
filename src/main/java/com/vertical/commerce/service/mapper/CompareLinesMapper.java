package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CompareLinesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompareLines} and its DTO {@link CompareLinesDTO}.
 */
@Mapper(componentModel = "spring", uses = {StockItemsMapper.class, ComparesMapper.class})
public interface CompareLinesMapper extends EntityMapper<CompareLinesDTO, CompareLines> {

    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "stockItem.name", target = "stockItemName")
    @Mapping(source = "compare.id", target = "compareId")
    CompareLinesDTO toDto(CompareLines compareLines);

    @Mapping(source = "stockItemId", target = "stockItem")
    @Mapping(source = "compareId", target = "compare")
    CompareLines toEntity(CompareLinesDTO compareLinesDTO);

    default CompareLines fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompareLines compareLines = new CompareLines();
        compareLines.setId(id);
        return compareLines;
    }
}
