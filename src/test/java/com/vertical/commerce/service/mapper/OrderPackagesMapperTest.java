package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderPackagesMapperTest {

    private OrderPackagesMapper orderPackagesMapper;

    @BeforeEach
    public void setUp() {
        orderPackagesMapper = new OrderPackagesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(orderPackagesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(orderPackagesMapper.fromId(null)).isNull();
    }
}
