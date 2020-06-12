package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomersMapperTest {

    private CustomersMapper customersMapper;

    @BeforeEach
    public void setUp() {
        customersMapper = new CustomersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customersMapper.fromId(null)).isNull();
    }
}
