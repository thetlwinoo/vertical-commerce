package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WishlistLinesMapperTest {

    private WishlistLinesMapper wishlistLinesMapper;

    @BeforeEach
    public void setUp() {
        wishlistLinesMapper = new WishlistLinesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(wishlistLinesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wishlistLinesMapper.fromId(null)).isNull();
    }
}
