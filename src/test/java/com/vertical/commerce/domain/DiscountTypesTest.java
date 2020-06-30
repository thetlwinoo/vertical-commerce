package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class DiscountTypesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountTypes.class);
        DiscountTypes discountTypes1 = new DiscountTypes();
        discountTypes1.setId(1L);
        DiscountTypes discountTypes2 = new DiscountTypes();
        discountTypes2.setId(discountTypes1.getId());
        assertThat(discountTypes1).isEqualTo(discountTypes2);
        discountTypes2.setId(2L);
        assertThat(discountTypes1).isNotEqualTo(discountTypes2);
        discountTypes1.setId(null);
        assertThat(discountTypes1).isNotEqualTo(discountTypes2);
    }
}
