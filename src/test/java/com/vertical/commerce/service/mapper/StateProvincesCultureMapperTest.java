package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StateProvincesCultureMapperTest {

    private StateProvincesCultureMapper stateProvincesCultureMapper;

    @BeforeEach
    public void setUp() {
        stateProvincesCultureMapper = new StateProvincesCultureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(stateProvincesCultureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stateProvincesCultureMapper.fromId(null)).isNull();
    }
}
