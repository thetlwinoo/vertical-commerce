package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ShippingFeeChartTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingFeeChart.class);
        ShippingFeeChart shippingFeeChart1 = new ShippingFeeChart();
        shippingFeeChart1.setId(1L);
        ShippingFeeChart shippingFeeChart2 = new ShippingFeeChart();
        shippingFeeChart2.setId(shippingFeeChart1.getId());
        assertThat(shippingFeeChart1).isEqualTo(shippingFeeChart2);
        shippingFeeChart2.setId(2L);
        assertThat(shippingFeeChart1).isNotEqualTo(shippingFeeChart2);
        shippingFeeChart1.setId(null);
        assertThat(shippingFeeChart1).isNotEqualTo(shippingFeeChart2);
    }
}
