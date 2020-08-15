package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductsLocalizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductsLocalize.class);
        ProductsLocalize productsLocalize1 = new ProductsLocalize();
        productsLocalize1.setId(1L);
        ProductsLocalize productsLocalize2 = new ProductsLocalize();
        productsLocalize2.setId(productsLocalize1.getId());
        assertThat(productsLocalize1).isEqualTo(productsLocalize2);
        productsLocalize2.setId(2L);
        assertThat(productsLocalize1).isNotEqualTo(productsLocalize2);
        productsLocalize1.setId(null);
        assertThat(productsLocalize1).isNotEqualTo(productsLocalize2);
    }
}
