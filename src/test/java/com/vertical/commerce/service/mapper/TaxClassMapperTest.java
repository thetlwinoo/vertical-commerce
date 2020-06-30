package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxClassMapperTest {

    private TaxClassMapper taxClassMapper;

    @BeforeEach
    public void setUp() {
        taxClassMapper = new TaxClassMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(taxClassMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taxClassMapper.fromId(null)).isNull();
    }
}
