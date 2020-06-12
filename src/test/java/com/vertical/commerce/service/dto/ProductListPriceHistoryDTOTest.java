package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductListPriceHistoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductListPriceHistoryDTO.class);
        ProductListPriceHistoryDTO productListPriceHistoryDTO1 = new ProductListPriceHistoryDTO();
        productListPriceHistoryDTO1.setId(1L);
        ProductListPriceHistoryDTO productListPriceHistoryDTO2 = new ProductListPriceHistoryDTO();
        assertThat(productListPriceHistoryDTO1).isNotEqualTo(productListPriceHistoryDTO2);
        productListPriceHistoryDTO2.setId(productListPriceHistoryDTO1.getId());
        assertThat(productListPriceHistoryDTO1).isEqualTo(productListPriceHistoryDTO2);
        productListPriceHistoryDTO2.setId(2L);
        assertThat(productListPriceHistoryDTO1).isNotEqualTo(productListPriceHistoryDTO2);
        productListPriceHistoryDTO1.setId(null);
        assertThat(productListPriceHistoryDTO1).isNotEqualTo(productListPriceHistoryDTO2);
    }
}
