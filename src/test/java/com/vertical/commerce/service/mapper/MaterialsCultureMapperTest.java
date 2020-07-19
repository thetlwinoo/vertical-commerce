package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MaterialsCultureMapperTest {

    private MaterialsCultureMapper materialsCultureMapper;

    @BeforeEach
    public void setUp() {
        materialsCultureMapper = new MaterialsCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(materialsCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(materialsCultureMapper.fromId(null)).isNull();
    }
}
