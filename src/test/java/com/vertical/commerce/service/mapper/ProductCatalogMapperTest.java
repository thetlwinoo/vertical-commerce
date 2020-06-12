package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductCatalogMapperTest {

    private ProductCatalogMapper productCatalogMapper;

    @BeforeEach
    public void setUp() {
        productCatalogMapper = new ProductCatalogMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productCatalogMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productCatalogMapper.fromId(null)).isNull();
    }
}
