package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerPaymentBankTransferMapperTest {

    private CustomerPaymentBankTransferMapper customerPaymentBankTransferMapper;

    @BeforeEach
    public void setUp() {
        customerPaymentBankTransferMapper = new CustomerPaymentBankTransferMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customerPaymentBankTransferMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customerPaymentBankTransferMapper.fromId(null)).isNull();
    }
}
