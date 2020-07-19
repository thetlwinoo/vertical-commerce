package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductsCultureMapperTest {

    private ProductsCultureMapper productsCultureMapper;

    @BeforeEach
    public void setUp() {
        productsCultureMapper = new ProductsCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productsCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productsCultureMapper.fromId(null)).isNull();
    }
}
