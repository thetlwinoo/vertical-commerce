package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BarcodeTypesMapperTest {

    private BarcodeTypesMapper barcodeTypesMapper;

    @BeforeEach
    public void setUp() {
        barcodeTypesMapper = new BarcodeTypesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(barcodeTypesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(barcodeTypesMapper.fromId(null)).isNull();
    }
}
