package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TownsMapperTest {

    private TownsMapper townsMapper;

    @BeforeEach
    public void setUp() {
        townsMapper = new TownsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(townsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(townsMapper.fromId(null)).isNull();
    }
}
