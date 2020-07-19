package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TownshipsCultureMapperTest {

    private TownshipsCultureMapper townshipsCultureMapper;

    @BeforeEach
    public void setUp() {
        townshipsCultureMapper = new TownshipsCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(townshipsCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(townshipsCultureMapper.fromId(null)).isNull();
    }
}
