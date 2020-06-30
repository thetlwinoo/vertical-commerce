package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DiscountTypesMapperTest {

    private DiscountTypesMapper discountTypesMapper;

    @BeforeEach
    public void setUp() {
        discountTypesMapper = new DiscountTypesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(discountTypesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(discountTypesMapper.fromId(null)).isNull();
    }
}
