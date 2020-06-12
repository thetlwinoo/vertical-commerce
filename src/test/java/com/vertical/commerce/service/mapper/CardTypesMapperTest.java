package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CardTypesMapperTest {

    private CardTypesMapper cardTypesMapper;

    @BeforeEach
    public void setUp() {
        cardTypesMapper = new CardTypesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cardTypesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cardTypesMapper.fromId(null)).isNull();
    }
}
