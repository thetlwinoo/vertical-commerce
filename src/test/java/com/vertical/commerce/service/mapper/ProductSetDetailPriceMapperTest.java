package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSetDetailPriceMapperTest {

    private ProductSetDetailPriceMapper productSetDetailPriceMapper;

    @BeforeEach
    public void setUp() {
        productSetDetailPriceMapper = new ProductSetDetailPriceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productSetDetailPriceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productSetDetailPriceMapper.fromId(null)).isNull();
    }
}
