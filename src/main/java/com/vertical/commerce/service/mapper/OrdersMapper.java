package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.OrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Orders} and its DTO {@link OrdersDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomersMapper.class, AddressesMapper.class, CurrencyRateMapper.class, PaymentMethodsMapper.class, PeopleMapper.class, SpecialDealsMapper.class})
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "shipToAddress.id", target = "shipToAddressId")
    @Mapping(source = "billToAddress.id", target = "billToAddressId")
    @Mapping(source = "currencyRate.id", target = "currencyRateId")
    @Mapping(source = "paymentMethod.id", target = "paymentMethodId")
    @Mapping(source = "paymentMethod.name", target = "paymentMethodName")
    @Mapping(source = "salePerson.id", target = "salePersonId")
    @Mapping(source = "salePerson.fullName", target = "salePersonFullName")
    @Mapping(source = "specialDeals.id", target = "specialDealsId")
    OrdersDTO toDto(Orders orders);

    @Mapping(target = "orderPackageLists", ignore = true)
    @Mapping(target = "removeOrderPackageList", ignore = true)
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "shipToAddressId", target = "shipToAddress")
    @Mapping(source = "billToAddressId", target = "billToAddress")
    @Mapping(source = "currencyRateId", target = "currencyRate")
    @Mapping(source = "paymentMethodId", target = "paymentMethod")
    @Mapping(source = "salePersonId", target = "salePerson")
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
