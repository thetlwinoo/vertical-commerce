package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderTrackingMapperTest {

    private OrderTrackingMapper orderTrackingMapper;

    @BeforeEach
    public void setUp() {
        orderTrackingMapper = new OrderTrackingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(orderTrackingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(orderTrackingMapper.fromId(null)).isNull();
    }
}
