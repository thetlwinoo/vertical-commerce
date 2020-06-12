package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductSetDetailPriceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSetDetailPrice.class);
        ProductSetDetailPrice productSetDetailPrice1 = new ProductSetDetailPrice();
        productSetDetailPrice1.setId(1L);
        ProductSetDetailPrice productSetDetailPrice2 = new ProductSetDetailPrice();
        productSetDetailPrice2.setId(productSetDetailPrice1.getId());
        assertThat(productSetDetailPrice1).isEqualTo(productSetDetailPrice2);
        productSetDetailPrice2.setId(2L);
        assertThat(productSetDetailPrice1).isNotEqualTo(productSetDetailPrice2);
        productSetDetailPrice1.setId(null);
        assertThat(productSetDetailPrice1).isNotEqualTo(productSetDetailPrice2);
    }
}
