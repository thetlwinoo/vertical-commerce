package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RegionsLocalizeMapperTest {

    private RegionsLocalizeMapper regionsLocalizeMapper;

    @BeforeEach
    public void setUp() {
        regionsLocalizeMapper = new RegionsLocalizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(regionsLocalizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(regionsLocalizeMapper.fromId(null)).isNull();
    }
}
