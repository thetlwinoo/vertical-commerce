package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class DiscountsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Discounts.class);
        Discounts discounts1 = new Discounts();
        discounts1.setId(1L);
        Discounts discounts2 = new Discounts();
        discounts2.setId(discounts1.getId());
        assertThat(discounts1).isEqualTo(discounts2);
        discounts2.setId(2L);
        assertThat(discounts1).isNotEqualTo(discounts2);
        discounts1.setId(null);
        assertThat(discounts1).isNotEqualTo(discounts2);
    }
}
