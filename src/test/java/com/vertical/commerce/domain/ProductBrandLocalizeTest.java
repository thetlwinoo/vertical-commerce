package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductBrandLocalizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductBrandLocalize.class);
        ProductBrandLocalize productBrandLocalize1 = new ProductBrandLocalize();
        productBrandLocalize1.setId(1L);
        ProductBrandLocalize productBrandLocalize2 = new ProductBrandLocalize();
        productBrandLocalize2.setId(productBrandLocalize1.getId());
        assertThat(productBrandLocalize1).isEqualTo(productBrandLocalize2);
        productBrandLocalize2.setId(2L);
        assertThat(productBrandLocalize1).isNotEqualTo(productBrandLocalize2);
        productBrandLocalize1.setId(null);
        assertThat(productBrandLocalize1).isNotEqualTo(productBrandLocalize2);
    }
}
