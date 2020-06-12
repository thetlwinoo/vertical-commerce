package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AddressTypesMapperTest {

    private AddressTypesMapper addressTypesMapper;

    @BeforeEach
    public void setUp() {
        addressTypesMapper = new AddressTypesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(addressTypesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(addressTypesMapper.fromId(null)).isNull();
    }
}
