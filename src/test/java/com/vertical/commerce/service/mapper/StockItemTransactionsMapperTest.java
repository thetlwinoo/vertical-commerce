package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StockItemTransactionsMapperTest {

    private StockItemTransactionsMapper stockItemTransactionsMapper;

    @BeforeEach
    public void setUp() {
        stockItemTransactionsMapper = new StockItemTransactionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(stockItemTransactionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stockItemTransactionsMapper.fromId(null)).isNull();
    }
}
