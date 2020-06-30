package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingFeeChartMapperTest {

    private ShippingFeeChartMapper shippingFeeChartMapper;

    @BeforeEach
    public void setUp() {
        shippingFeeChartMapper = new ShippingFeeChartMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(shippingFeeChartMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(shippingFeeChartMapper.fromId(null)).isNull();
    }
}
