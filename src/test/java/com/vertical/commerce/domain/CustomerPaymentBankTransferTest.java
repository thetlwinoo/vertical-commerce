package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CustomerPaymentBankTransferTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPaymentBankTransfer.class);
        CustomerPaymentBankTransfer customerPaymentBankTransfer1 = new CustomerPaymentBankTransfer();
        customerPaymentBankTransfer1.setId(1L);
        CustomerPaymentBankTransfer customerPaymentBankTransfer2 = new CustomerPaymentBankTransfer();
        customerPaymentBankTransfer2.setId(customerPaymentBankTransfer1.getId());
        assertThat(customerPaymentBankTransfer1).isEqualTo(customerPaymentBankTransfer2);
        customerPaymentBankTransfer2.setId(2L);
        assertThat(customerPaymentBankTransfer1).isNotEqualTo(customerPaymentBankTransfer2);
        customerPaymentBankTransfer1.setId(null);
        assertThat(customerPaymentBankTransfer1).isNotEqualTo(customerPaymentBankTransfer2);
    }
}
