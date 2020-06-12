package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CardTypeCreditCardsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardTypeCreditCards.class);
        CardTypeCreditCards cardTypeCreditCards1 = new CardTypeCreditCards();
        cardTypeCreditCards1.setId(1L);
        CardTypeCreditCards cardTypeCreditCards2 = new CardTypeCreditCards();
        cardTypeCreditCards2.setId(cardTypeCreditCards1.getId());
        assertThat(cardTypeCreditCards1).isEqualTo(cardTypeCreditCards2);
        cardTypeCreditCards2.setId(2L);
        assertThat(cardTypeCreditCards1).isNotEqualTo(cardTypeCreditCards2);
        cardTypeCreditCards1.setId(null);
        assertThat(cardTypeCreditCards1).isNotEqualTo(cardTypeCreditCards2);
    }
}
