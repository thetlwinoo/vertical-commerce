package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CustomerPaymentCreditCardExtendedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPaymentCreditCardExtended.class);
        CustomerPaymentCreditCardExtended customerPaymentCreditCardExtended1 = new CustomerPaymentCreditCardExtended();
        customerPaymentCreditCardExtended1.setId(1L);
        CustomerPaymentCreditCardExtended customerPaymentCreditCardExtended2 = new CustomerPaymentCreditCardExtended();
        customerPaymentCreditCardExtended2.setId(customerPaymentCreditCardExtended1.getId());
        assertThat(customerPaymentCreditCardExtended1).isEqualTo(customerPaymentCreditCardExtended2);
        customerPaymentCreditCardExtended2.setId(2L);
        assertThat(customerPaymentCreditCardExtended1).isNotEqualTo(customerPaymentCreditCardExtended2);
        customerPaymentCreditCardExtended1.setId(null);
        assertThat(customerPaymentCreditCardExtended1).isNotEqualTo(customerPaymentCreditCardExtended2);
    }
}
