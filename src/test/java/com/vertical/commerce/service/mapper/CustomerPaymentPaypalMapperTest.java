package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerPaymentPaypalMapperTest {

    private CustomerPaymentPaypalMapper customerPaymentPaypalMapper;

    @BeforeEach
    public void setUp() {
        customerPaymentPaypalMapper = new CustomerPaymentPaypalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customerPaymentPaypalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customerPaymentPaypalMapper.fromId(null)).isNull();
    }
}
