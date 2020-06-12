package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BankAccountsMapperTest {

    private BankAccountsMapper bankAccountsMapper;

    @BeforeEach
    public void setUp() {
        bankAccountsMapper = new BankAccountsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bankAccountsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bankAccountsMapper.fromId(null)).isNull();
    }
}
