package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerPaymentCreditCard} and its DTO {@link CustomerPaymentCreditCardDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerPaymentMapper.class, CurrencyMapper.class})
public interface CustomerPaymentCreditCardMapper extends EntityMapper<CustomerPaymentCreditCardDTO, CustomerPaymentCreditCard> {

    @Mapping(source = "customerPayment.id", target = "customerPaymentId")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.name", target = "currencyName")
    CustomerPaymentCreditCardDTO toDto(CustomerPaymentCreditCard customerPaymentCreditCard);

    @Mapping(source = "customerPaymentId", target = "customerPayment")
    @Mapping(source = "currencyId", target = "currency")
    CustomerPaymentCreditCard toEntity(CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO);

    default CustomerPaymentCreditCard fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerPaymentCreditCard customerPaymentCreditCard = new CustomerPaymentCreditCard();
        customerPaymentCreditCard.setId(id);
        return customerPaymentCreditCard;
    }
}
