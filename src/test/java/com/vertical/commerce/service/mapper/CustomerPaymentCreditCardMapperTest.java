package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerPaymentCreditCardMapperTest {

    private CustomerPaymentCreditCardMapper customerPaymentCreditCardMapper;

    @BeforeEach
    public void setUp() {
        customerPaymentCreditCardMapper = new CustomerPaymentCreditCardMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customerPaymentCreditCardMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customerPaymentCreditCardMapper.fromId(null)).isNull();
    }
}
