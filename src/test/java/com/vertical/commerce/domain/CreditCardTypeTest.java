package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CreditCardTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCardType.class);
        CreditCardType creditCardType1 = new CreditCardType();
        creditCardType1.setId(1L);
        CreditCardType creditCardType2 = new CreditCardType();
        creditCardType2.setId(creditCardType1.getId());
        assertThat(creditCardType1).isEqualTo(creditCardType2);
        creditCardType2.setId(2L);
        assertThat(creditCardType1).isNotEqualTo(creditCardType2);
        creditCardType1.setId(null);
        assertThat(creditCardType1).isNotEqualTo(creditCardType2);
    }
}
