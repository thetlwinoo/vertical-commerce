package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDocumentsMapperTest {

    private ProductDocumentsMapper productDocumentsMapper;

    @BeforeEach
    public void setUp() {
        productDocumentsMapper = new ProductDocumentsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productDocumentsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productDocumentsMapper.fromId(null)).isNull();
    }
}
