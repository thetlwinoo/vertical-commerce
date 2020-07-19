package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDocumentsCultureMapperTest {

    private ProductDocumentsCultureMapper productDocumentsCultureMapper;

    @BeforeEach
    public void setUp() {
        productDocumentsCultureMapper = new ProductDocumentsCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productDocumentsCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productDocumentsCultureMapper.fromId(null)).isNull();
    }
}
