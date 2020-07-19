package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DistrictsCultureMapperTest {

    private DistrictsCultureMapper districtsCultureMapper;

    @BeforeEach
    public void setUp() {
        districtsCultureMapper = new DistrictsCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(districtsCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(districtsCultureMapper.fromId(null)).isNull();
    }
}
