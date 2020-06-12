package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReceiptsMapperTest {

    private ReceiptsMapper receiptsMapper;

    @BeforeEach
    public void setUp() {
        receiptsMapper = new ReceiptsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(receiptsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(receiptsMapper.fromId(null)).isNull();
    }
}
