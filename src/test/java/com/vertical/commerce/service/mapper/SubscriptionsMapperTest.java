package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SubscriptionsMapperTest {

    private SubscriptionsMapper subscriptionsMapper;

    @BeforeEach
    public void setUp() {
        subscriptionsMapper = new SubscriptionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(subscriptionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(subscriptionsMapper.fromId(null)).isNull();
    }
}
