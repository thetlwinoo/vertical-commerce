package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseOrderLinesMapperTest {

    private PurchaseOrderLinesMapper purchaseOrderLinesMapper;

    @BeforeEach
    public void setUp() {
        purchaseOrderLinesMapper = new PurchaseOrderLinesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(purchaseOrderLinesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(purchaseOrderLinesMapper.fromId(null)).isNull();
    }
}
