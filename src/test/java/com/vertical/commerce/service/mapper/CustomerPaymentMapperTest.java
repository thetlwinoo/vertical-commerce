package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerPaymentMapperTest {

    private CustomerPaymentMapper customerPaymentMapper;

    @BeforeEach
    public void setUp() {
        customerPaymentMapper = new CustomerPaymentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customerPaymentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customerPaymentMapper.fromId(null)).isNull();
    }
}
