package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.OrderLinesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderLines} and its DTO {@link OrderLinesDTO}.
 */
@Mapper(componentModel = "spring", uses = {SuppliersMapper.class, StockItemsMapper.class, PackageTypesMapper.class, PhotosMapper.class, OrdersMapper.class})
public interface OrderLinesMapper extends EntityMapper<OrderLinesDTO, OrderLines> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "stockItem.name", target = "stockItemName")
    @Mapping(source = "packageType.id", target = "packageTypeId")
    @Mapping(source = "packageType.name", target = "packageTypeName")
    @Mapping(source = "reviewImage.id", target = "reviewImageId")
    @Mapping(source = "reviewImage.thumbnailUrl", target = "reviewImageThumbnailUrl")
    @Mapping(source = "order.id", target = "orderId")
    OrderLinesDTO toDto(OrderLines orderLines);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "stockItemId", target = "stockItem")
    @Mapping(source = "packageTypeId", target = "packageType")
    @Mapping(source = "reviewImageId", target = "reviewImage")
    @Mapping(source = "orderId", target = "order")
    OrderLines toEntity(OrderLinesDTO orderLinesDTO);

    default OrderLines fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderLines orderLines = new OrderLines();
        orderLines.setId(id);
        return orderLines;
    }
}
