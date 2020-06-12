package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CustomerPaymentVoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerPaymentVoucher} and its DTO {@link CustomerPaymentVoucherDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerPaymentMapper.class, CurrencyMapper.class})
public interface CustomerPaymentVoucherMapper extends EntityMapper<CustomerPaymentVoucherDTO, CustomerPaymentVoucher> {

    @Mapping(source = "customerPayment.id", target = "customerPaymentId")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.name", target = "currencyName")
    CustomerPaymentVoucherDTO toDto(CustomerPaymentVoucher customerPaymentVoucher);

    @Mapping(source = "customerPaymentId", target = "customerPayment")
    @Mapping(source = "currencyId", target = "currency")
    CustomerPaymentVoucher toEntity(CustomerPaymentVoucherDTO customerPaymentVoucherDTO);

    default CustomerPaymentVoucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerPaymentVoucher customerPaymentVoucher = new CustomerPaymentVoucher();
        customerPaymentVoucher.setId(id);
        return customerPaymentVoucher;
    }
}
