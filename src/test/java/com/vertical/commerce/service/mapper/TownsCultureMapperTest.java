package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TownsCultureMapperTest {

    private TownsCultureMapper townsCultureMapper;

    @BeforeEach
    public void setUp() {
        townsCultureMapper = new TownsCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(townsCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(townsCultureMapper.fromId(null)).isNull();
    }
}
