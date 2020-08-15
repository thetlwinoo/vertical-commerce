package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CitiesLocalizeMapperTest {

    private CitiesLocalizeMapper citiesLocalizeMapper;

    @BeforeEach
    public void setUp() {
        citiesLocalizeMapper = new CitiesLocalizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(citiesLocalizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(citiesLocalizeMapper.fromId(null)).isNull();
    }
}
