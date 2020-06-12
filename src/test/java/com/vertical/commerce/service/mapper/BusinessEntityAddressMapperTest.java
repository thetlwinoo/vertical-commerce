package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BusinessEntityAddressMapperTest {

    private BusinessEntityAddressMapper businessEntityAddressMapper;

    @BeforeEach
    public void setUp() {
        businessEntityAddressMapper = new BusinessEntityAddressMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(businessEntityAddressMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(businessEntityAddressMapper.fromId(null)).isNull();
    }
}
