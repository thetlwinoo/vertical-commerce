package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TownshipsLocalizeMapperTest {

    private TownshipsLocalizeMapper townshipsLocalizeMapper;

    @BeforeEach
    public void setUp() {
        townshipsLocalizeMapper = new TownshipsLocalizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(townshipsLocalizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(townshipsLocalizeMapper.fromId(null)).isNull();
    }
}
