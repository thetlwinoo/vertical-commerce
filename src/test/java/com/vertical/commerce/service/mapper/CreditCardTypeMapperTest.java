package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CreditCardTypeMapperTest {

    private CreditCardTypeMapper creditCardTypeMapper;

    @BeforeEach
    public void setUp() {
        creditCardTypeMapper = new CreditCardTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(creditCardTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(creditCardTypeMapper.fromId(null)).isNull();
    }
}
