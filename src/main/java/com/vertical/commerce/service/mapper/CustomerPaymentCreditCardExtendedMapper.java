package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardExtendedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerPaymentCreditCardExtended} and its DTO {@link CustomerPaymentCreditCardExtendedDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerPaymentCreditCardExtendedMapper extends EntityMapper<CustomerPaymentCreditCardExtendedDTO, CustomerPaymentCreditCardExtended> {



    default CustomerPaymentCreditCardExtended fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerPaymentCreditCardExtended customerPaymentCreditCardExtended = new CustomerPaymentCreditCardExtended();
        customerPaymentCreditCardExtended.setId(id);
        return customerPaymentCreditCardExtended;
    }
}
