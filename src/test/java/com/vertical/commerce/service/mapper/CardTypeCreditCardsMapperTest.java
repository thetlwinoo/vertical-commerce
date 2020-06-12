package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CardTypeCreditCardsMapperTest {

    private CardTypeCreditCardsMapper cardTypeCreditCardsMapper;

    @BeforeEach
    public void setUp() {
        cardTypeCreditCardsMapper = new CardTypeCreditCardsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cardTypeCreditCardsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cardTypeCreditCardsMapper.fromId(null)).isNull();
    }
}
