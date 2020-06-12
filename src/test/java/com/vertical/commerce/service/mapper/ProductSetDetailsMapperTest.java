package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSetDetailsMapperTest {

    private ProductSetDetailsMapper productSetDetailsMapper;

    @BeforeEach
    public void setUp() {
        productSetDetailsMapper = new ProductSetDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productSetDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productSetDetailsMapper.fromId(null)).isNull();
    }
}
