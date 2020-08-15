package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductsLocalizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductsLocalizeDTO.class);
        ProductsLocalizeDTO productsLocalizeDTO1 = new ProductsLocalizeDTO();
        productsLocalizeDTO1.setId(1L);
        ProductsLocalizeDTO productsLocalizeDTO2 = new ProductsLocalizeDTO();
        assertThat(productsLocalizeDTO1).isNotEqualTo(productsLocalizeDTO2);
        productsLocalizeDTO2.setId(productsLocalizeDTO1.getId());
        assertThat(productsLocalizeDTO1).isEqualTo(productsLocalizeDTO2);
        productsLocalizeDTO2.setId(2L);
        assertThat(productsLocalizeDTO1).isNotEqualTo(productsLocalizeDTO2);
        productsLocalizeDTO1.setId(null);
        assertThat(productsLocalizeDTO1).isNotEqualTo(productsLocalizeDTO2);
    }
}
