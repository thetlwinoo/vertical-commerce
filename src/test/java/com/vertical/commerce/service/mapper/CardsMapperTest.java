package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CardsMapperTest {

    private CardsMapper cardsMapper;

    @BeforeEach
    public void setUp() {
        cardsMapper = new CardsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cardsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cardsMapper.fromId(null)).isNull();
    }
}
