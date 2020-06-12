package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TrackingEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrackingEvent.class);
        TrackingEvent trackingEvent1 = new TrackingEvent();
        trackingEvent1.setId(1L);
        TrackingEvent trackingEvent2 = new TrackingEvent();
        trackingEvent2.setId(trackingEvent1.getId());
        assertThat(trackingEvent1).isEqualTo(trackingEvent2);
        trackingEvent2.setId(2L);
        assertThat(trackingEvent1).isNotEqualTo(trackingEvent2);
        trackingEvent1.setId(null);
        assertThat(trackingEvent1).isNotEqualTo(trackingEvent2);
    }
}
