package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductBrandCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductBrandCulture.class);
        ProductBrandCulture productBrandCulture1 = new ProductBrandCulture();
        productBrandCulture1.setId(1L);
        ProductBrandCulture productBrandCulture2 = new ProductBrandCulture();
        productBrandCulture2.setId(productBrandCulture1.getId());
        assertThat(productBrandCulture1).isEqualTo(productBrandCulture2);
        productBrandCulture2.setId(2L);
        assertThat(productBrandCulture1).isNotEqualTo(productBrandCulture2);
        productBrandCulture1.setId(null);
        assertThat(productBrandCulture1).isNotEqualTo(productBrandCulture2);
    }
}
