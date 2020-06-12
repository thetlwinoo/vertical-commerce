package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CustomerPaymentPaypalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPaymentPaypal.class);
        CustomerPaymentPaypal customerPaymentPaypal1 = new CustomerPaymentPaypal();
        customerPaymentPaypal1.setId(1L);
        CustomerPaymentPaypal customerPaymentPaypal2 = new CustomerPaymentPaypal();
        customerPaymentPaypal2.setId(customerPaymentPaypal1.getId());
        assertThat(customerPaymentPaypal1).isEqualTo(customerPaymentPaypal2);
        customerPaymentPaypal2.setId(2L);
        assertThat(customerPaymentPaypal1).isNotEqualTo(customerPaymentPaypal2);
        customerPaymentPaypal1.setId(null);
        assertThat(customerPaymentPaypal1).isNotEqualTo(customerPaymentPaypal2);
    }
}
