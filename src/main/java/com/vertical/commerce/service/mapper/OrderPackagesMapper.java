package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.OrderPackagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderPackages} and its DTO {@link OrderPackagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {SuppliersMapper.class, DeliveryMethodsMapper.class, SpecialDealsMapper.class, OrdersMapper.class})
public interface OrderPackagesMapper extends EntityMapper<OrderPackagesDTO, OrderPackages> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "deliveryMethod.id", target = "deliveryMethodId")
    @Mapping(source = "deliveryMethod.name", target = "deliveryMethodName")
    @Mapping(source = "specialDeals.id", target = "specialDealsId")
    @Mapping(source = "order.id", target = "orderId")
    OrderPackagesDTO toDto(OrderPackages orderPackages);

    @Mapping(target = "orderLineLists", ignore = true)
    @Mapping(target = "removeOrderLineList", ignore = true)
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "deliveryMethodId", target = "deliveryMethod")
    @Mapping(source = "specialDealsId", target = "specialDeals")
    @Mapping(source = "orderId", target = "order")
    OrderPackages toEntity(OrderPackagesDTO orderPackagesDTO);

    default OrderPackages fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderPackages orderPackages = new OrderPackages();
        orderPackages.setId(id);
        return orderPackages;
    }
}
