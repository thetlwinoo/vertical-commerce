package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CampaignTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Campaign.class);
        Campaign campaign1 = new Campaign();
        campaign1.setId(1L);
        Campaign campaign2 = new Campaign();
        campaign2.setId(campaign1.getId());
        assertThat(campaign1).isEqualTo(campaign2);
        campaign2.setId(2L);
        assertThat(campaign1).isNotEqualTo(campaign2);
        campaign1.setId(null);
        assertThat(campaign1).isNotEqualTo(campaign2);
    }
}
