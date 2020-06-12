package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentMethodsMapperTest {

    private PaymentMethodsMapper paymentMethodsMapper;

    @BeforeEach
    public void setUp() {
        paymentMethodsMapper = new PaymentMethodsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paymentMethodsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paymentMethodsMapper.fromId(null)).isNull();
    }
}
