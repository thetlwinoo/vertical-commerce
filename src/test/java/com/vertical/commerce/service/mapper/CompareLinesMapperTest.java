package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompareLinesMapperTest {

    private CompareLinesMapper compareLinesMapper;

    @BeforeEach
    public void setUp() {
        compareLinesMapper = new CompareLinesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(compareLinesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(compareLinesMapper.fromId(null)).isNull();
    }
}
