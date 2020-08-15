package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductsLocalizeMapperTest {

    private ProductsLocalizeMapper productsLocalizeMapper;

    @BeforeEach
    public void setUp() {
        productsLocalizeMapper = new ProductsLocalizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productsLocalizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productsLocalizeMapper.fromId(null)).isNull();
    }
}
