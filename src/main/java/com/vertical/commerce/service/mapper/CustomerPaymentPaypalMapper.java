package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CustomerPaymentPaypalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerPaymentPaypal} and its DTO {@link CustomerPaymentPaypalDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerPaymentMapper.class, CurrencyMapper.class})
public interface CustomerPaymentPaypalMapper extends EntityMapper<CustomerPaymentPaypalDTO, CustomerPaymentPaypal> {

    @Mapping(source = "customerPayment.id", target = "customerPaymentId")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.name", target = "currencyName")
    CustomerPaymentPaypalDTO toDto(CustomerPaymentPaypal customerPaymentPaypal);

    @Mapping(source = "customerPaymentId", target = "customerPayment")
    @Mapping(source = "currencyId", target = "currency")
    CustomerPaymentPaypal toEntity(CustomerPaymentPaypalDTO customerPaymentPaypalDTO);

    default CustomerPaymentPaypal fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerPaymentPaypal customerPaymentPaypal = new CustomerPaymentPaypal();
        customerPaymentPaypal.setId(id);
        return customerPaymentPaypal;
    }
}
