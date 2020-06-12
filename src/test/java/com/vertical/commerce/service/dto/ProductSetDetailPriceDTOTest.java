package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductSetDetailPriceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSetDetailPriceDTO.class);
        ProductSetDetailPriceDTO productSetDetailPriceDTO1 = new ProductSetDetailPriceDTO();
        productSetDetailPriceDTO1.setId(1L);
        ProductSetDetailPriceDTO productSetDetailPriceDTO2 = new ProductSetDetailPriceDTO();
        assertThat(productSetDetailPriceDTO1).isNotEqualTo(productSetDetailPriceDTO2);
        productSetDetailPriceDTO2.setId(productSetDetailPriceDTO1.getId());
        assertThat(productSetDetailPriceDTO1).isEqualTo(productSetDetailPriceDTO2);
        productSetDetailPriceDTO2.setId(2L);
        assertThat(productSetDetailPriceDTO1).isNotEqualTo(productSetDetailPriceDTO2);
        productSetDetailPriceDTO1.setId(null);
        assertThat(productSetDetailPriceDTO1).isNotEqualTo(productSetDetailPriceDTO2);
    }
}
