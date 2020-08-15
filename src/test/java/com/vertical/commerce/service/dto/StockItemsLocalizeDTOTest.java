package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class StockItemsLocalizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemsLocalizeDTO.class);
        StockItemsLocalizeDTO stockItemsLocalizeDTO1 = new StockItemsLocalizeDTO();
        stockItemsLocalizeDTO1.setId(1L);
        StockItemsLocalizeDTO stockItemsLocalizeDTO2 = new StockItemsLocalizeDTO();
        assertThat(stockItemsLocalizeDTO1).isNotEqualTo(stockItemsLocalizeDTO2);
        stockItemsLocalizeDTO2.setId(stockItemsLocalizeDTO1.getId());
        assertThat(stockItemsLocalizeDTO1).isEqualTo(stockItemsLocalizeDTO2);
        stockItemsLocalizeDTO2.setId(2L);
        assertThat(stockItemsLocalizeDTO1).isNotEqualTo(stockItemsLocalizeDTO2);
        stockItemsLocalizeDTO1.setId(null);
        assertThat(stockItemsLocalizeDTO1).isNotEqualTo(stockItemsLocalizeDTO2);
    }
}
