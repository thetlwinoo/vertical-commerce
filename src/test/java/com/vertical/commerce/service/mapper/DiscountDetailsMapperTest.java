package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DiscountDetailsMapperTest {

    private DiscountDetailsMapper discountDetailsMapper;

    @BeforeEach
    public void setUp() {
        discountDetailsMapper = new DiscountDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(discountDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(discountDetailsMapper.fromId(null)).isNull();
    }
}
