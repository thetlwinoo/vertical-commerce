package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CustomerPaymentVoucherDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPaymentVoucherDTO.class);
        CustomerPaymentVoucherDTO customerPaymentVoucherDTO1 = new CustomerPaymentVoucherDTO();
        customerPaymentVoucherDTO1.setId(1L);
        CustomerPaymentVoucherDTO customerPaymentVoucherDTO2 = new CustomerPaymentVoucherDTO();
        assertThat(customerPaymentVoucherDTO1).isNotEqualTo(customerPaymentVoucherDTO2);
        customerPaymentVoucherDTO2.setId(customerPaymentVoucherDTO1.getId());
        assertThat(customerPaymentVoucherDTO1).isEqualTo(customerPaymentVoucherDTO2);
        customerPaymentVoucherDTO2.setId(2L);
        assertThat(customerPaymentVoucherDTO1).isNotEqualTo(customerPaymentVoucherDTO2);
        customerPaymentVoucherDTO1.setId(null);
        assertThat(customerPaymentVoucherDTO1).isNotEqualTo(customerPaymentVoucherDTO2);
    }
}
