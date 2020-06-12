package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CustomerPaymentBankTransferDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerPaymentBankTransfer} and its DTO {@link CustomerPaymentBankTransferDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerPaymentMapper.class, CurrencyMapper.class})
public interface CustomerPaymentBankTransferMapper extends EntityMapper<CustomerPaymentBankTransferDTO, CustomerPaymentBankTransfer> {

    @Mapping(source = "customerPayment.id", target = "customerPaymentId")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.name", target = "currencyName")
    CustomerPaymentBankTransferDTO toDto(CustomerPaymentBankTransfer customerPaymentBankTransfer);

    @Mapping(source = "customerPaymentId", target = "customerPayment")
    @Mapping(source = "currencyId", target = "currency")
    CustomerPaymentBankTransfer toEntity(CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO);

    default CustomerPaymentBankTransfer fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerPaymentBankTransfer customerPaymentBankTransfer = new CustomerPaymentBankTransfer();
        customerPaymentBankTransfer.setId(id);
        return customerPaymentBankTransfer;
    }
}
