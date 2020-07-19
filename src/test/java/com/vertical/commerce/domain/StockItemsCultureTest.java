package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class StockItemsCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemsCulture.class);
        StockItemsCulture stockItemsCulture1 = new StockItemsCulture();
        stockItemsCulture1.setId(1L);
        StockItemsCulture stockItemsCulture2 = new StockItemsCulture();
        stockItemsCulture2.setId(stockItemsCulture1.getId());
        assertThat(stockItemsCulture1).isEqualTo(stockItemsCulture2);
        stockItemsCulture2.setId(2L);
        assertThat(stockItemsCulture1).isNotEqualTo(stockItemsCulture2);
        stockItemsCulture1.setId(null);
        assertThat(stockItemsCulture1).isNotEqualTo(stockItemsCulture2);
    }
}
