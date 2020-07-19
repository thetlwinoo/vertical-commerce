package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DiscountsMapperTest {

    private DiscountsMapper discountsMapper;

    @BeforeEach
    public void setUp() {
        discountsMapper = new DiscountsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(discountsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(discountsMapper.fromId(null)).isNull();
    }
}
