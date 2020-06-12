package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductBrandMapperTest {

    private ProductBrandMapper productBrandMapper;

    @BeforeEach
    public void setUp() {
        productBrandMapper = new ProductBrandMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productBrandMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productBrandMapper.fromId(null)).isNull();
    }
}
