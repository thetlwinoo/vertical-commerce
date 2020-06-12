package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CardTypesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardTypes.class);
        CardTypes cardTypes1 = new CardTypes();
        cardTypes1.setId(1L);
        CardTypes cardTypes2 = new CardTypes();
        cardTypes2.setId(cardTypes1.getId());
        assertThat(cardTypes1).isEqualTo(cardTypes2);
        cardTypes2.setId(2L);
        assertThat(cardTypes1).isNotEqualTo(cardTypes2);
        cardTypes1.setId(null);
        assertThat(cardTypes1).isNotEqualTo(cardTypes2);
    }
}
