package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class StockItemsCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemsCultureDTO.class);
        StockItemsCultureDTO stockItemsCultureDTO1 = new StockItemsCultureDTO();
        stockItemsCultureDTO1.setId(1L);
        StockItemsCultureDTO stockItemsCultureDTO2 = new StockItemsCultureDTO();
        assertThat(stockItemsCultureDTO1).isNotEqualTo(stockItemsCultureDTO2);
        stockItemsCultureDTO2.setId(stockItemsCultureDTO1.getId());
        assertThat(stockItemsCultureDTO1).isEqualTo(stockItemsCultureDTO2);
        stockItemsCultureDTO2.setId(2L);
        assertThat(stockItemsCultureDTO1).isNotEqualTo(stockItemsCultureDTO2);
        stockItemsCultureDTO1.setId(null);
        assertThat(stockItemsCultureDTO1).isNotEqualTo(stockItemsCultureDTO2);
    }
}
