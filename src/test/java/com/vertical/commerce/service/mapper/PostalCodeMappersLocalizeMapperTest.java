package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PostalCodeMappersLocalizeMapperTest {

    private PostalCodeMappersLocalizeMapper postalCodeMappersLocalizeMapper;

    @BeforeEach
    public void setUp() {
        postalCodeMappersLocalizeMapper = new PostalCodeMappersLocalizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(postalCodeMappersLocalizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(postalCodeMappersLocalizeMapper.fromId(null)).isNull();
    }
}
