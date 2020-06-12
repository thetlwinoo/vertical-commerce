package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PhotosMapperTest {

    private PhotosMapper photosMapper;

    @BeforeEach
    public void setUp() {
        photosMapper = new PhotosMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(photosMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(photosMapper.fromId(null)).isNull();
    }
}
