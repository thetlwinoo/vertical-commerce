package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CreditCardTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCardTypeDTO.class);
        CreditCardTypeDTO creditCardTypeDTO1 = new CreditCardTypeDTO();
        creditCardTypeDTO1.setId(1L);
        CreditCardTypeDTO creditCardTypeDTO2 = new CreditCardTypeDTO();
        assertThat(creditCardTypeDTO1).isNotEqualTo(creditCardTypeDTO2);
        creditCardTypeDTO2.setId(creditCardTypeDTO1.getId());
        assertThat(creditCardTypeDTO1).isEqualTo(creditCardTypeDTO2);
        creditCardTypeDTO2.setId(2L);
        assertThat(creditCardTypeDTO1).isNotEqualTo(creditCardTypeDTO2);
        creditCardTypeDTO1.setId(null);
        assertThat(creditCardTypeDTO1).isNotEqualTo(creditCardTypeDTO2);
    }
}
