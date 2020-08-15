package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductCategoryLocalizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategoryLocalize.class);
        ProductCategoryLocalize productCategoryLocalize1 = new ProductCategoryLocalize();
        productCategoryLocalize1.setId(1L);
        ProductCategoryLocalize productCategoryLocalize2 = new ProductCategoryLocalize();
        productCategoryLocalize2.setId(productCategoryLocalize1.getId());
        assertThat(productCategoryLocalize1).isEqualTo(productCategoryLocalize2);
        productCategoryLocalize2.setId(2L);
        assertThat(productCategoryLocalize1).isNotEqualTo(productCategoryLocalize2);
        productCategoryLocalize1.setId(null);
        assertThat(productCategoryLocalize1).isNotEqualTo(productCategoryLocalize2);
    }
}
