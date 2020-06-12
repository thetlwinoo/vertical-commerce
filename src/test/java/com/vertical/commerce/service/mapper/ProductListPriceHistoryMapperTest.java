package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductListPriceHistoryMapperTest {

    private ProductListPriceHistoryMapper productListPriceHistoryMapper;

    @BeforeEach
    public void setUp() {
        productListPriceHistoryMapper = new ProductListPriceHistoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productListPriceHistoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productListPriceHistoryMapper.fromId(null)).isNull();
    }
}
