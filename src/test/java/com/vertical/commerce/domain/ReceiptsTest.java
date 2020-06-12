package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ReceiptsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Receipts.class);
        Receipts receipts1 = new Receipts();
        receipts1.setId(1L);
        Receipts receipts2 = new Receipts();
        receipts2.setId(receipts1.getId());
        assertThat(receipts1).isEqualTo(receipts2);
        receipts2.setId(2L);
        assertThat(receipts1).isNotEqualTo(receipts2);
        receipts1.setId(null);
        assertThat(receipts1).isNotEqualTo(receipts2);
    }
}
