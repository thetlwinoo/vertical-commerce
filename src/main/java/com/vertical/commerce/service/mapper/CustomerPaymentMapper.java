package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CustomerPaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerPayment} and its DTO {@link CustomerPaymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerTransactionsMapper.class, PaymentMethodsMapper.class, CurrencyMapper.class, CurrencyRateMapper.class})
public interface CustomerPaymentMapper extends EntityMapper<CustomerPaymentDTO, CustomerPayment> {

    @Mapping(source = "customerTransaction.id", target = "customerTransactionId")
    @Mapping(source = "paymentMethod.id", target = "paymentMethodId")
    @Mapping(source = "paymentMethod.name", target = "paymentMethodName")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.name", target = "currencyName")
    @Mapping(source = "currencyRate.id", target = "currencyRateId")
    CustomerPaymentDTO toDto(CustomerPayment customerPayment);

    @Mapping(source = "customerTransactionId", target = "customerTransaction")
    @Mapping(source = "paymentMethodId", target = "paymentMethod")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "currencyRateId", target = "currencyRate")
    @Mapping(target = "customerPaymentCreditCard", ignore = true)
    @Mapping(target = "customerPaymentVoucher", ignore = true)
    @Mapping(target = "customerPaymentBankTransfer", ignore = true)
    @Mapping(target = "customerPaymentPaypal", ignore = true)
    CustomerPayment toEntity(CustomerPaymentDTO customerPaymentDTO);

    default CustomerPayment fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerPayment customerPayment = new CustomerPayment();
        customerPayment.setId(id);
        return customerPayment;
    }
}
