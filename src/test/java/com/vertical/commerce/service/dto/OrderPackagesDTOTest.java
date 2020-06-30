package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class OrderPackagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderPackagesDTO.class);
        OrderPackagesDTO orderPackagesDTO1 = new OrderPackagesDTO();
        orderPackagesDTO1.setId(1L);
        OrderPackagesDTO orderPackagesDTO2 = new OrderPackagesDTO();
        assertThat(orderPackagesDTO1).isNotEqualTo(orderPackagesDTO2);
        orderPackagesDTO2.setId(orderPackagesDTO1.getId());
        assertThat(orderPackagesDTO1).isEqualTo(orderPackagesDTO2);
        orderPackagesDTO2.setId(2L);
        assertThat(orderPackagesDTO1).isNotEqualTo(orderPackagesDTO2);
        orderPackagesDTO1.setId(null);
        assertThat(orderPackagesDTO1).isNotEqualTo(orderPackagesDTO2);
    }
}
