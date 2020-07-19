package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RegionsCultureMapperTest {

    private RegionsCultureMapper regionsCultureMapper;

    @BeforeEach
    public void setUp() {
        regionsCultureMapper = new RegionsCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(regionsCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(regionsCultureMapper.fromId(null)).isNull();
    }
}
