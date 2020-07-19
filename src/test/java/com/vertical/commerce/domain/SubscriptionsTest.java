package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class SubscriptionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subscriptions.class);
        Subscriptions subscriptions1 = new Subscriptions();
        subscriptions1.setId(1L);
        Subscriptions subscriptions2 = new Subscriptions();
        subscriptions2.setId(subscriptions1.getId());
        assertThat(subscriptions1).isEqualTo(subscriptions2);
        subscriptions2.setId(2L);
        assertThat(subscriptions1).isNotEqualTo(subscriptions2);
        subscriptions1.setId(null);
        assertThat(subscriptions1).isNotEqualTo(subscriptions2);
    }
}
