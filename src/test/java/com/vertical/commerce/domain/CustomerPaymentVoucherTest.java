package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CustomerPaymentVoucherTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPaymentVoucher.class);
        CustomerPaymentVoucher customerPaymentVoucher1 = new CustomerPaymentVoucher();
        customerPaymentVoucher1.setId(1L);
        CustomerPaymentVoucher customerPaymentVoucher2 = new CustomerPaymentVoucher();
        customerPaymentVoucher2.setId(customerPaymentVoucher1.getId());
        assertThat(customerPaymentVoucher1).isEqualTo(customerPaymentVoucher2);
        customerPaymentVoucher2.setId(2L);
        assertThat(customerPaymentVoucher1).isNotEqualTo(customerPaymentVoucher2);
        customerPaymentVoucher1.setId(null);
        assertThat(customerPaymentVoucher1).isNotEqualTo(customerPaymentVoucher2);
    }
}
