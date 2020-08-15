package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PostalCodeMappersMapperTest {

    private PostalCodeMappersMapper postalCodeMappersMapper;

    @BeforeEach
    public void setUp() {
        postalCodeMappersMapper = new PostalCodeMappersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(postalCodeMappersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(postalCodeMappersMapper.fromId(null)).isNull();
    }
}
