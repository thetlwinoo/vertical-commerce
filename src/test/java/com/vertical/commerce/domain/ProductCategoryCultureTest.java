package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductCategoryCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategoryCulture.class);
        ProductCategoryCulture productCategoryCulture1 = new ProductCategoryCulture();
        productCategoryCulture1.setId(1L);
        ProductCategoryCulture productCategoryCulture2 = new ProductCategoryCulture();
        productCategoryCulture2.setId(productCategoryCulture1.getId());
        assertThat(productCategoryCulture1).isEqualTo(productCategoryCulture2);
        productCategoryCulture2.setId(2L);
        assertThat(productCategoryCulture1).isNotEqualTo(productCategoryCulture2);
        productCategoryCulture1.setId(null);
        assertThat(productCategoryCulture1).isNotEqualTo(productCategoryCulture2);
    }
}
