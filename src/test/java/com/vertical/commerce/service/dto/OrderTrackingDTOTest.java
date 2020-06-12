package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class OrderTrackingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTrackingDTO.class);
        OrderTrackingDTO orderTrackingDTO1 = new OrderTrackingDTO();
        orderTrackingDTO1.setId(1L);
        OrderTrackingDTO orderTrackingDTO2 = new OrderTrackingDTO();
        assertThat(orderTrackingDTO1).isNotEqualTo(orderTrackingDTO2);
        orderTrackingDTO2.setId(orderTrackingDTO1.getId());
        assertThat(orderTrackingDTO1).isEqualTo(orderTrackingDTO2);
        orderTrackingDTO2.setId(2L);
        assertThat(orderTrackingDTO1).isNotEqualTo(orderTrackingDTO2);
        orderTrackingDTO1.setId(null);
        assertThat(orderTrackingDTO1).isNotEqualTo(orderTrackingDTO2);
    }
}
