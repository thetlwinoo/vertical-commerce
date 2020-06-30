package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class OrderPackagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderPackages.class);
        OrderPackages orderPackages1 = new OrderPackages();
        orderPackages1.setId(1L);
        OrderPackages orderPackages2 = new OrderPackages();
        orderPackages2.setId(orderPackages1.getId());
        assertThat(orderPackages1).isEqualTo(orderPackages2);
        orderPackages2.setId(2L);
        assertThat(orderPackages1).isNotEqualTo(orderPackages2);
        orderPackages1.setId(null);
        assertThat(orderPackages1).isNotEqualTo(orderPackages2);
    }
}
