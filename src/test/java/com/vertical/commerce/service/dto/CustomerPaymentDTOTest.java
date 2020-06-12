package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CustomerPaymentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPaymentDTO.class);
        CustomerPaymentDTO customerPaymentDTO1 = new CustomerPaymentDTO();
        customerPaymentDTO1.setId(1L);
        CustomerPaymentDTO customerPaymentDTO2 = new CustomerPaymentDTO();
        assertThat(customerPaymentDTO1).isNotEqualTo(customerPaymentDTO2);
        customerPaymentDTO2.setId(customerPaymentDTO1.getId());
        assertThat(customerPaymentDTO1).isEqualTo(customerPaymentDTO2);
        customerPaymentDTO2.setId(2L);
        assertThat(customerPaymentDTO1).isNotEqualTo(customerPaymentDTO2);
        customerPaymentDTO1.setId(null);
        assertThat(customerPaymentDTO1).isNotEqualTo(customerPaymentDTO2);
    }
}
