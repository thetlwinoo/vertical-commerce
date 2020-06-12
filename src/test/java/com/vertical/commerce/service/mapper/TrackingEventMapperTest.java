package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TrackingEventMapperTest {

    private TrackingEventMapper trackingEventMapper;

    @BeforeEach
    public void setUp() {
        trackingEventMapper = new TrackingEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(trackingEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(trackingEventMapper.fromId(null)).isNull();
    }
}
