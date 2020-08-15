package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TownsLocalizeMapperTest {

    private TownsLocalizeMapper townsLocalizeMapper;

    @BeforeEach
    public void setUp() {
        townsLocalizeMapper = new TownsLocalizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(townsLocalizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(townsLocalizeMapper.fromId(null)).isNull();
    }
}
