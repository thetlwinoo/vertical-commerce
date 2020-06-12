package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductAttributeSetMapperTest {

    private ProductAttributeSetMapper productAttributeSetMapper;

    @BeforeEach
    public void setUp() {
        productAttributeSetMapper = new ProductAttributeSetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productAttributeSetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productAttributeSetMapper.fromId(null)).isNull();
    }
}
