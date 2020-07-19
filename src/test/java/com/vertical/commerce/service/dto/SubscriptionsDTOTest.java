package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class SubscriptionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionsDTO.class);
        SubscriptionsDTO subscriptionsDTO1 = new SubscriptionsDTO();
        subscriptionsDTO1.setId(1L);
        SubscriptionsDTO subscriptionsDTO2 = new SubscriptionsDTO();
        assertThat(subscriptionsDTO1).isNotEqualTo(subscriptionsDTO2);
        subscriptionsDTO2.setId(subscriptionsDTO1.getId());
        assertThat(subscriptionsDTO1).isEqualTo(subscriptionsDTO2);
        subscriptionsDTO2.setId(2L);
        assertThat(subscriptionsDTO1).isNotEqualTo(subscriptionsDTO2);
        subscriptionsDTO1.setId(null);
        assertThat(subscriptionsDTO1).isNotEqualTo(subscriptionsDTO2);
    }
}
