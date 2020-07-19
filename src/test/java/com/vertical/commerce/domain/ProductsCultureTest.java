package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductsCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductsCulture.class);
        ProductsCulture productsCulture1 = new ProductsCulture();
        productsCulture1.setId(1L);
        ProductsCulture productsCulture2 = new ProductsCulture();
        productsCulture2.setId(productsCulture1.getId());
        assertThat(productsCulture1).isEqualTo(productsCulture2);
        productsCulture2.setId(2L);
        assertThat(productsCulture1).isNotEqualTo(productsCulture2);
        productsCulture1.setId(null);
        assertThat(productsCulture1).isNotEqualTo(productsCulture2);
    }
}
