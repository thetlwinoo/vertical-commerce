package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class SupplierTransactionStatusDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierTransactionStatusDTO.class);
        SupplierTransactionStatusDTO supplierTransactionStatusDTO1 = new SupplierTransactionStatusDTO();
        supplierTransactionStatusDTO1.setId(1L);
        SupplierTransactionStatusDTO supplierTransactionStatusDTO2 = new SupplierTransactionStatusDTO();
        assertThat(supplierTransactionStatusDTO1).isNotEqualTo(supplierTransactionStatusDTO2);
        supplierTransactionStatusDTO2.setId(supplierTransactionStatusDTO1.getId());
        assertThat(supplierTransactionStatusDTO1).isEqualTo(supplierTransactionStatusDTO2);
        supplierTransactionStatusDTO2.setId(2L);
        assertThat(supplierTransactionStatusDTO1).isNotEqualTo(supplierTransactionStatusDTO2);
        supplierTransactionStatusDTO1.setId(null);
        assertThat(supplierTransactionStatusDTO1).isNotEqualTo(supplierTransactionStatusDTO2);
    }
}
