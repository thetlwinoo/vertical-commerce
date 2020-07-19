package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StockItemsCultureMapperTest {

    private StockItemsCultureMapper stockItemsCultureMapper;

    @BeforeEach
    public void setUp() {
        stockItemsCultureMapper = new StockItemsCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(stockItemsCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stockItemsCultureMapper.fromId(null)).isNull();
    }
}
