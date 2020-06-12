package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CampaignMapperTest {

    private CampaignMapper campaignMapper;

    @BeforeEach
    public void setUp() {
        campaignMapper = new CampaignMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(campaignMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(campaignMapper.fromId(null)).isNull();
    }
}
