package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerPaymentVoucherMapperTest {

    private CustomerPaymentVoucherMapper customerPaymentVoucherMapper;

    @BeforeEach
    public void setUp() {
        customerPaymentVoucherMapper = new CustomerPaymentVoucherMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customerPaymentVoucherMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customerPaymentVoucherMapper.fromId(null)).isNull();
    }
}
