package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class SupplierTransactionStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierTransactionStatus.class);
        SupplierTransactionStatus supplierTransactionStatus1 = new SupplierTransactionStatus();
        supplierTransactionStatus1.setId(1L);
        SupplierTransactionStatus supplierTransactionStatus2 = new SupplierTransactionStatus();
        supplierTransactionStatus2.setId(supplierTransactionStatus1.getId());
        assertThat(supplierTransactionStatus1).isEqualTo(supplierTransactionStatus2);
        supplierTransactionStatus2.setId(2L);
        assertThat(supplierTransactionStatus1).isNotEqualTo(supplierTransactionStatus2);
        supplierTransactionStatus1.setId(null);
        assertThat(supplierTransactionStatus1).isNotEqualTo(supplierTransactionStatus2);
    }
}
