package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductCategoryLocalizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategoryLocalizeDTO.class);
        ProductCategoryLocalizeDTO productCategoryLocalizeDTO1 = new ProductCategoryLocalizeDTO();
        productCategoryLocalizeDTO1.setId(1L);
        ProductCategoryLocalizeDTO productCategoryLocalizeDTO2 = new ProductCategoryLocalizeDTO();
        assertThat(productCategoryLocalizeDTO1).isNotEqualTo(productCategoryLocalizeDTO2);
        productCategoryLocalizeDTO2.setId(productCategoryLocalizeDTO1.getId());
        assertThat(productCategoryLocalizeDTO1).isEqualTo(productCategoryLocalizeDTO2);
        productCategoryLocalizeDTO2.setId(2L);
        assertThat(productCategoryLocalizeDTO1).isNotEqualTo(productCategoryLocalizeDTO2);
        productCategoryLocalizeDTO1.setId(null);
        assertThat(productCategoryLocalizeDTO1).isNotEqualTo(productCategoryLocalizeDTO2);
    }
}
