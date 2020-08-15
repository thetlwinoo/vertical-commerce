package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductCategoryLocalizeMapperTest {

    private ProductCategoryLocalizeMapper productCategoryLocalizeMapper;

    @BeforeEach
    public void setUp() {
        productCategoryLocalizeMapper = new ProductCategoryLocalizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productCategoryLocalizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productCategoryLocalizeMapper.fromId(null)).isNull();
    }
}
