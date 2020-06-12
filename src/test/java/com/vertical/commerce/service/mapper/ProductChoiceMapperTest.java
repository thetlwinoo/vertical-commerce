package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductChoiceMapperTest {

    private ProductChoiceMapper productChoiceMapper;

    @BeforeEach
    public void setUp() {
        productChoiceMapper = new ProductChoiceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productChoiceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productChoiceMapper.fromId(null)).isNull();
    }
}
