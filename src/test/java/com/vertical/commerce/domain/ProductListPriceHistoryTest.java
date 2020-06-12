package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductListPriceHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductListPriceHistory.class);
        ProductListPriceHistory productListPriceHistory1 = new ProductListPriceHistory();
        productListPriceHistory1.setId(1L);
        ProductListPriceHistory productListPriceHistory2 = new ProductListPriceHistory();
        productListPriceHistory2.setId(productListPriceHistory1.getId());
        assertThat(productListPriceHistory1).isEqualTo(productListPriceHistory2);
        productListPriceHistory2.setId(2L);
        assertThat(productListPriceHistory1).isNotEqualTo(productListPriceHistory2);
        productListPriceHistory1.setId(null);
        assertThat(productListPriceHistory1).isNotEqualTo(productListPriceHistory2);
    }
}
