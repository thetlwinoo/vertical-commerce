package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TownshipsMapperTest {

    private TownshipsMapper townshipsMapper;

    @BeforeEach
    public void setUp() {
        townshipsMapper = new TownshipsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(townshipsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(townshipsMapper.fromId(null)).isNull();
    }
}
