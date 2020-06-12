package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class OrderTrackingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTracking.class);
        OrderTracking orderTracking1 = new OrderTracking();
        orderTracking1.setId(1L);
        OrderTracking orderTracking2 = new OrderTracking();
        orderTracking2.setId(orderTracking1.getId());
        assertThat(orderTracking1).isEqualTo(orderTracking2);
        orderTracking2.setId(2L);
        assertThat(orderTracking1).isNotEqualTo(orderTracking2);
        orderTracking1.setId(null);
        assertThat(orderTracking1).isNotEqualTo(orderTracking2);
    }
}
