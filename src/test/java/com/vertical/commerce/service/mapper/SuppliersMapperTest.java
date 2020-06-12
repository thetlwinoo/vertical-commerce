package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SuppliersMapperTest {

    private SuppliersMapper suppliersMapper;

    @BeforeEach
    public void setUp() {
        suppliersMapper = new SuppliersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(suppliersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(suppliersMapper.fromId(null)).isNull();
    }
}
