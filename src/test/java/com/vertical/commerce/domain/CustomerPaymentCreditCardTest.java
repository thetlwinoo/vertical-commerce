package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CustomerPaymentCreditCardTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPaymentCreditCard.class);
        CustomerPaymentCreditCard customerPaymentCreditCard1 = new CustomerPaymentCreditCard();
        customerPaymentCreditCard1.setId(1L);
        CustomerPaymentCreditCard customerPaymentCreditCard2 = new CustomerPaymentCreditCard();
        customerPaymentCreditCard2.setId(customerPaymentCreditCard1.getId());
        assertThat(customerPaymentCreditCard1).isEqualTo(customerPaymentCreditCard2);
        customerPaymentCreditCard2.setId(2L);
        assertThat(customerPaymentCreditCard1).isNotEqualTo(customerPaymentCreditCard2);
        customerPaymentCreditCard1.setId(null);
        assertThat(customerPaymentCreditCard1).isNotEqualTo(customerPaymentCreditCard2);
    }
}
