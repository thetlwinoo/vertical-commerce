package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class PaymentMethodsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentMethodsDTO.class);
        PaymentMethodsDTO paymentMethodsDTO1 = new PaymentMethodsDTO();
        paymentMethodsDTO1.setId(1L);
        PaymentMethodsDTO paymentMethodsDTO2 = new PaymentMethodsDTO();
        assertThat(paymentMethodsDTO1).isNotEqualTo(paymentMethodsDTO2);
        paymentMethodsDTO2.setId(paymentMethodsDTO1.getId());
        assertThat(paymentMethodsDTO1).isEqualTo(paymentMethodsDTO2);
        paymentMethodsDTO2.setId(2L);
        assertThat(paymentMethodsDTO1).isNotEqualTo(paymentMethodsDTO2);
        paymentMethodsDTO1.setId(null);
        assertThat(paymentMethodsDTO1).isNotEqualTo(paymentMethodsDTO2);
    }
}
