package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CustomerPaymentBankTransferDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPaymentBankTransferDTO.class);
        CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO1 = new CustomerPaymentBankTransferDTO();
        customerPaymentBankTransferDTO1.setId(1L);
        CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO2 = new CustomerPaymentBankTransferDTO();
        assertThat(customerPaymentBankTransferDTO1).isNotEqualTo(customerPaymentBankTransferDTO2);
        customerPaymentBankTransferDTO2.setId(customerPaymentBankTransferDTO1.getId());
        assertThat(customerPaymentBankTransferDTO1).isEqualTo(customerPaymentBankTransferDTO2);
        customerPaymentBankTransferDTO2.setId(2L);
        assertThat(customerPaymentBankTransferDTO1).isNotEqualTo(customerPaymentBankTransferDTO2);
        customerPaymentBankTransferDTO1.setId(null);
        assertThat(customerPaymentBankTransferDTO1).isNotEqualTo(customerPaymentBankTransferDTO2);
    }
}
