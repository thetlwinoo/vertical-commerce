package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TrackingEventDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrackingEventDTO.class);
        TrackingEventDTO trackingEventDTO1 = new TrackingEventDTO();
        trackingEventDTO1.setId(1L);
        TrackingEventDTO trackingEventDTO2 = new TrackingEventDTO();
        assertThat(trackingEventDTO1).isNotEqualTo(trackingEventDTO2);
        trackingEventDTO2.setId(trackingEventDTO1.getId());
        assertThat(trackingEventDTO1).isEqualTo(trackingEventDTO2);
        trackingEventDTO2.setId(2L);
        assertThat(trackingEventDTO1).isNotEqualTo(trackingEventDTO2);
        trackingEventDTO1.setId(null);
        assertThat(trackingEventDTO1).isNotEqualTo(trackingEventDTO2);
    }
}
