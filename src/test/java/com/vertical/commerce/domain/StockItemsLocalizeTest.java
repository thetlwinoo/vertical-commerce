package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class StockItemsLocalizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemsLocalize.class);
        StockItemsLocalize stockItemsLocalize1 = new StockItemsLocalize();
        stockItemsLocalize1.setId(1L);
        StockItemsLocalize stockItemsLocalize2 = new StockItemsLocalize();
        stockItemsLocalize2.setId(stockItemsLocalize1.getId());
        assertThat(stockItemsLocalize1).isEqualTo(stockItemsLocalize2);
        stockItemsLocalize2.setId(2L);
        assertThat(stockItemsLocalize1).isNotEqualTo(stockItemsLocalize2);
        stockItemsLocalize1.setId(null);
        assertThat(stockItemsLocalize1).isNotEqualTo(stockItemsLocalize2);
    }
}
