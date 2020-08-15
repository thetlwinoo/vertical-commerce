package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StockItemsLocalizeMapperTest {

    private StockItemsLocalizeMapper stockItemsLocalizeMapper;

    @BeforeEach
    public void setUp() {
        stockItemsLocalizeMapper = new StockItemsLocalizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(stockItemsLocalizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stockItemsLocalizeMapper.fromId(null)).isNull();
    }
}
