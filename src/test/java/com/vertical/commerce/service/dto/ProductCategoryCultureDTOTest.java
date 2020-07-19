package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductCategoryCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategoryCultureDTO.class);
        ProductCategoryCultureDTO productCategoryCultureDTO1 = new ProductCategoryCultureDTO();
        productCategoryCultureDTO1.setId(1L);
        ProductCategoryCultureDTO productCategoryCultureDTO2 = new ProductCategoryCultureDTO();
        assertThat(productCategoryCultureDTO1).isNotEqualTo(productCategoryCultureDTO2);
        productCategoryCultureDTO2.setId(productCategoryCultureDTO1.getId());
        assertThat(productCategoryCultureDTO1).isEqualTo(productCategoryCultureDTO2);
        productCategoryCultureDTO2.setId(2L);
        assertThat(productCategoryCultureDTO1).isNotEqualTo(productCategoryCultureDTO2);
        productCategoryCultureDTO1.setId(null);
        assertThat(productCategoryCultureDTO1).isNotEqualTo(productCategoryCultureDTO2);
    }
}
