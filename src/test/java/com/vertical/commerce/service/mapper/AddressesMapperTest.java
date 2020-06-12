package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AddressesMapperTest {

    private AddressesMapper addressesMapper;

    @BeforeEach
    public void setUp() {
        addressesMapper = new AddressesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(addressesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(addressesMapper.fromId(null)).isNull();
    }
}
