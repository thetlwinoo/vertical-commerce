package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductBrandLocalizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductBrandLocalizeDTO.class);
        ProductBrandLocalizeDTO productBrandLocalizeDTO1 = new ProductBrandLocalizeDTO();
        productBrandLocalizeDTO1.setId(1L);
        ProductBrandLocalizeDTO productBrandLocalizeDTO2 = new ProductBrandLocalizeDTO();
        assertThat(productBrandLocalizeDTO1).isNotEqualTo(productBrandLocalizeDTO2);
        productBrandLocalizeDTO2.setId(productBrandLocalizeDTO1.getId());
        assertThat(productBrandLocalizeDTO1).isEqualTo(productBrandLocalizeDTO2);
        productBrandLocalizeDTO2.setId(2L);
        assertThat(productBrandLocalizeDTO1).isNotEqualTo(productBrandLocalizeDTO2);
        productBrandLocalizeDTO1.setId(null);
        assertThat(productBrandLocalizeDTO1).isNotEqualTo(productBrandLocalizeDTO2);
    }
}
