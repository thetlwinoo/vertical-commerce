package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class DiscountDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountDetails.class);
        DiscountDetails discountDetails1 = new DiscountDetails();
        discountDetails1.setId(1L);
        DiscountDetails discountDetails2 = new DiscountDetails();
        discountDetails2.setId(discountDetails1.getId());
        assertThat(discountDetails1).isEqualTo(discountDetails2);
        discountDetails2.setId(2L);
        assertThat(discountDetails1).isNotEqualTo(discountDetails2);
        discountDetails1.setId(null);
        assertThat(discountDetails1).isNotEqualTo(discountDetails2);
    }
}
