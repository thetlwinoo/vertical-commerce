package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductCategoryCultureMapperTest {

    private ProductCategoryCultureMapper productCategoryCultureMapper;

    @BeforeEach
    public void setUp() {
        productCategoryCultureMapper = new ProductCategoryCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productCategoryCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productCategoryCultureMapper.fromId(null)).isNull();
    }
}
