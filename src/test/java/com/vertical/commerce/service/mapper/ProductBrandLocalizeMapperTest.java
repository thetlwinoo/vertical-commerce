package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductBrandLocalizeMapperTest {

    private ProductBrandLocalizeMapper productBrandLocalizeMapper;

    @BeforeEach
    public void setUp() {
        productBrandLocalizeMapper = new ProductBrandLocalizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productBrandLocalizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productBrandLocalizeMapper.fromId(null)).isNull();
    }
}
