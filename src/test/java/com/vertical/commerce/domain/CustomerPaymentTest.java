package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CustomerPaymentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPayment.class);
        CustomerPayment customerPayment1 = new CustomerPayment();
        customerPayment1.setId(1L);
        CustomerPayment customerPayment2 = new CustomerPayment();
        customerPayment2.setId(customerPayment1.getId());
        assertThat(customerPayment1).isEqualTo(customerPayment2);
        customerPayment2.setId(2L);
        assertThat(customerPayment1).isNotEqualTo(customerPayment2);
        customerPayment1.setId(null);
        assertThat(customerPayment1).isNotEqualTo(customerPayment2);
    }
}
