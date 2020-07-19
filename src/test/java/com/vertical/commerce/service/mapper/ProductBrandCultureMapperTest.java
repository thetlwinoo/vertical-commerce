package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductBrandCultureMapperTest {

    private ProductBrandCultureMapper productBrandCultureMapper;

    @BeforeEach
    public void setUp() {
        productBrandCultureMapper = new ProductBrandCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productBrandCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productBrandCultureMapper.fromId(null)).isNull();
    }
}
