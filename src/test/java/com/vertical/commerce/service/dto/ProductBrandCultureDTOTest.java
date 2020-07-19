package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductBrandCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductBrandCultureDTO.class);
        ProductBrandCultureDTO productBrandCultureDTO1 = new ProductBrandCultureDTO();
        productBrandCultureDTO1.setId(1L);
        ProductBrandCultureDTO productBrandCultureDTO2 = new ProductBrandCultureDTO();
        assertThat(productBrandCultureDTO1).isNotEqualTo(productBrandCultureDTO2);
        productBrandCultureDTO2.setId(productBrandCultureDTO1.getId());
        assertThat(productBrandCultureDTO1).isEqualTo(productBrandCultureDTO2);
        productBrandCultureDTO2.setId(2L);
        assertThat(productBrandCultureDTO1).isNotEqualTo(productBrandCultureDTO2);
        productBrandCultureDTO1.setId(null);
        assertThat(productBrandCultureDTO1).isNotEqualTo(productBrandCultureDTO2);
    }
}
