package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SupplierTransactionStatusMapperTest {

    private SupplierTransactionStatusMapper supplierTransactionStatusMapper;

    @BeforeEach
    public void setUp() {
        supplierTransactionStatusMapper = new SupplierTransactionStatusMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(supplierTransactionStatusMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(supplierTransactionStatusMapper.fromId(null)).isNull();
    }
}
