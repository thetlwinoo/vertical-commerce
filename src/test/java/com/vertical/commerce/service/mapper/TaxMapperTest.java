package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxMapperTest {

    private TaxMapper taxMapper;

    @BeforeEach
    public void setUp() {
        taxMapper = new TaxMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(taxMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taxMapper.fromId(null)).isNull();
    }
}
