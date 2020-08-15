package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MaterialsLocalizeMapperTest {

    private MaterialsLocalizeMapper materialsLocalizeMapper;

    @BeforeEach
    public void setUp() {
        materialsLocalizeMapper = new MaterialsLocalizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(materialsLocalizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(materialsLocalizeMapper.fromId(null)).isNull();
    }
}
