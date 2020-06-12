package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CustomerPaymentCreditCardExtendedDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPaymentCreditCardExtendedDTO.class);
        CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO1 = new CustomerPaymentCreditCardExtendedDTO();
        customerPaymentCreditCardExtendedDTO1.setId(1L);
        CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO2 = new CustomerPaymentCreditCardExtendedDTO();
        assertThat(customerPaymentCreditCardExtendedDTO1).isNotEqualTo(customerPaymentCreditCardExtendedDTO2);
        customerPaymentCreditCardExtendedDTO2.setId(customerPaymentCreditCardExtendedDTO1.getId());
        assertThat(customerPaymentCreditCardExtendedDTO1).isEqualTo(customerPaymentCreditCardExtendedDTO2);
        customerPaymentCreditCardExtendedDTO2.setId(2L);
        assertThat(customerPaymentCreditCardExtendedDTO1).isNotEqualTo(customerPaymentCreditCardExtendedDTO2);
        customerPaymentCreditCardExtendedDTO1.setId(null);
        assertThat(customerPaymentCreditCardExtendedDTO1).isNotEqualTo(customerPaymentCreditCardExtendedDTO2);
    }
}
