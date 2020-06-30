package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ShippingFeeChartDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingFeeChartDTO.class);
        ShippingFeeChartDTO shippingFeeChartDTO1 = new ShippingFeeChartDTO();
        shippingFeeChartDTO1.setId(1L);
        ShippingFeeChartDTO shippingFeeChartDTO2 = new ShippingFeeChartDTO();
        assertThat(shippingFeeChartDTO1).isNotEqualTo(shippingFeeChartDTO2);
        shippingFeeChartDTO2.setId(shippingFeeChartDTO1.getId());
        assertThat(shippingFeeChartDTO1).isEqualTo(shippingFeeChartDTO2);
        shippingFeeChartDTO2.setId(2L);
        assertThat(shippingFeeChartDTO1).isNotEqualTo(shippingFeeChartDTO2);
        shippingFeeChartDTO1.setId(null);
        assertThat(shippingFeeChartDTO1).isNotEqualTo(shippingFeeChartDTO2);
    }
}
