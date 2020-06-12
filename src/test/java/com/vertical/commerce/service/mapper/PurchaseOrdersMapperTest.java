package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseOrdersMapperTest {

    private PurchaseOrdersMapper purchaseOrdersMapper;

    @BeforeEach
    public void setUp() {
        purchaseOrdersMapper = new PurchaseOrdersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(purchaseOrdersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(purchaseOrdersMapper.fromId(null)).isNull();
    }
}
