package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CustomerPaymentPaypalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPaymentPaypalDTO.class);
        CustomerPaymentPaypalDTO customerPaymentPaypalDTO1 = new CustomerPaymentPaypalDTO();
        customerPaymentPaypalDTO1.setId(1L);
        CustomerPaymentPaypalDTO customerPaymentPaypalDTO2 = new CustomerPaymentPaypalDTO();
        assertThat(customerPaymentPaypalDTO1).isNotEqualTo(customerPaymentPaypalDTO2);
        customerPaymentPaypalDTO2.setId(customerPaymentPaypalDTO1.getId());
        assertThat(customerPaymentPaypalDTO1).isEqualTo(customerPaymentPaypalDTO2);
        customerPaymentPaypalDTO2.setId(2L);
        assertThat(customerPaymentPaypalDTO1).isNotEqualTo(customerPaymentPaypalDTO2);
        customerPaymentPaypalDTO1.setId(null);
        assertThat(customerPaymentPaypalDTO1).isNotEqualTo(customerPaymentPaypalDTO2);
    }
}
