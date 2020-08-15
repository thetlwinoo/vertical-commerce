package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StateProvincesLocalizeMapperTest {

    private StateProvincesLocalizeMapper stateProvincesLocalizeMapper;

    @BeforeEach
    public void setUp() {
        stateProvincesLocalizeMapper = new StateProvincesLocalizeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(stateProvincesLocalizeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stateProvincesLocalizeMapper.fromId(null)).isNull();
    }
}
