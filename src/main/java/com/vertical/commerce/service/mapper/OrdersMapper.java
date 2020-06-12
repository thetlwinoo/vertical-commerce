package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.OrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Orders} and its DTO {@link OrdersDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomersMapper.class, AddressesMapper.class, ShipMethodMapper.class, CurrencyRateMapper.class, PaymentMethodsMapper.class, SpecialDealsMapper.class})
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "shipToAddress.id", target = "shipToAddressId")
    @Mapping(source = "billToAddress.id", target = "billToAddressId")
    @Mapping(source = "shipMethod.id", target = "shipMethodId")
    @Mapping(source = "shipMethod.name", target = "shipMethodName")
    @Mapping(source = "currencyRate.id", target = "currencyRateId")
    @Mapping(source = "paymentMethod.id", target = "paymentMethodId")
    @Mapping(source = "specialDeals.id", target = "specialDealsId")
    OrdersDTO toDto(Orders orders);

    @Mapping(target = "orderLineLists", ignore = true)
    @Mapping(target = "removeOrderLineList", ignore = true)
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "shipToAddressId", target = "shipToAddress")
    @Mapping(source = "billToAddressId", target = "billToAddress")
    @Mapping(source = "shipMethodId", target = "shipMethod")
    @Mapping(source = "currencyRateId", target = "currencyRate")
    @Mapping(source = "paymentMethodId", target = "paymentMethod")
    @Mapping(target = "orderTracking", ignore = true)
    @Mapping(source = "specialDealsId", target = "specialDeals")
    Orders toEntity(OrdersDTO ordersDTO);

    default Orders fromId(Long id) {
        if (id == null) {
            return null;
        }
        Orders orders = new Orders();
        orders.setId(id);
        return orders;
    }
}
