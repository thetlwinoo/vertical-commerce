package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BusinessEntityContactMapperTest {

    private BusinessEntityContactMapper businessEntityContactMapper;

    @BeforeEach
    public void setUp() {
        businessEntityContactMapper = new BusinessEntityContactMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(businessEntityContactMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(businessEntityContactMapper.fromId(null)).isNull();
    }
}
