package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CountriesLocalizeMapperTest {

    private CountriesLocalizeMapper countriesLocalizeMapper;

    @BeforeEach
    public void setUp() {
        countriesLocalizeMapper = new CountriesLocalizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(countriesLocalizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(countriesLocalizeMapper.fromId(null)).isNull();
    }
}
