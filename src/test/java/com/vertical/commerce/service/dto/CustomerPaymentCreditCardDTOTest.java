package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CustomerPaymentCreditCardDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPaymentCreditCardDTO.class);
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO1 = new CustomerPaymentCreditCardDTO();
        customerPaymentCreditCardDTO1.setId(1L);
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO2 = new CustomerPaymentCreditCardDTO();
        assertThat(customerPaymentCreditCardDTO1).isNotEqualTo(customerPaymentCreditCardDTO2);
        customerPaymentCreditCardDTO2.setId(customerPaymentCreditCardDTO1.getId());
        assertThat(customerPaymentCreditCardDTO1).isEqualTo(customerPaymentCreditCardDTO2);
        customerPaymentCreditCardDTO2.setId(2L);
        assertThat(customerPaymentCreditCardDTO1).isNotEqualTo(customerPaymentCreditCardDTO2);
        customerPaymentCreditCardDTO1.setId(null);
        assertThat(customerPaymentCreditCardDTO1).isNotEqualTo(customerPaymentCreditCardDTO2);
    }
}
