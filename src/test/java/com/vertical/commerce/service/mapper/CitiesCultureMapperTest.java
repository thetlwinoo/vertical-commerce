package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CitiesCultureMapperTest {

    private CitiesCultureMapper citiesCultureMapper;

    @BeforeEach
    public void setUp() {
        citiesCultureMapper = new CitiesCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(citiesCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(citiesCultureMapper.fromId(null)).isNull();
    }
}
