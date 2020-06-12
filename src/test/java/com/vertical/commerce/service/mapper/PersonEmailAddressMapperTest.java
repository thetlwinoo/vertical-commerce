package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonEmailAddressMapperTest {

    private PersonEmailAddressMapper personEmailAddressMapper;

    @BeforeEach
    public void setUp() {
        personEmailAddressMapper = new PersonEmailAddressMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(personEmailAddressMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(personEmailAddressMapper.fromId(null)).isNull();
    }
}
