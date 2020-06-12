package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DeliveryMethodsMapperTest {

    private DeliveryMethodsMapper deliveryMethodsMapper;

    @BeforeEach
    public void setUp() {
        deliveryMethodsMapper = new DeliveryMethodsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(deliveryMethodsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(deliveryMethodsMapper.fromId(null)).isNull();
    }
}
