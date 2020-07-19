package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CountriesCultureMapperTest {

    private CountriesCultureMapper countriesCultureMapper;

    @BeforeEach
    public void setUp() {
        countriesCultureMapper = new CountriesCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(countriesCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(countriesCultureMapper.fromId(null)).isNull();
    }
}
