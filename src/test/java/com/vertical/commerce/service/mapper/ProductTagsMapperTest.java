package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductTagsMapperTest {

    private ProductTagsMapper productTagsMapper;

    @BeforeEach
    public void setUp() {
        productTagsMapper = new ProductTagsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productTagsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productTagsMapper.fromId(null)).isNull();
    }
}
