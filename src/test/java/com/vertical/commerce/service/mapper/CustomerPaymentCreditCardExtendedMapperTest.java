package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerPaymentCreditCardExtendedMapperTest {

    private CustomerPaymentCreditCardExtendedMapper customerPaymentCreditCardExtendedMapper;

    @BeforeEach
    public void setUp() {
        customerPaymentCreditCardExtendedMapper = new CustomerPaymentCreditCardExtendedMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customerPaymentCreditCardExtendedMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customerPaymentCreditCardExtendedMapper.fromId(null)).isNull();
    }
}
