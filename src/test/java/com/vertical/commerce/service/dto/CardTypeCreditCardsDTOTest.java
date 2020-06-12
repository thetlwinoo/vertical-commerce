package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CardTypeCreditCardsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardTypeCreditCardsDTO.class);
        CardTypeCreditCardsDTO cardTypeCreditCardsDTO1 = new CardTypeCreditCardsDTO();
        cardTypeCreditCardsDTO1.setId(1L);
        CardTypeCreditCardsDTO cardTypeCreditCardsDTO2 = new CardTypeCreditCardsDTO();
        assertThat(cardTypeCreditCardsDTO1).isNotEqualTo(cardTypeCreditCardsDTO2);
        cardTypeCreditCardsDTO2.setId(cardTypeCreditCardsDTO1.getId());
        assertThat(cardTypeCreditCardsDTO1).isEqualTo(cardTypeCreditCardsDTO2);
        cardTypeCreditCardsDTO2.setId(2L);
        assertThat(cardTypeCreditCardsDTO1).isNotEqualTo(cardTypeCreditCardsDTO2);
        cardTypeCreditCardsDTO1.setId(null);
        assertThat(cardTypeCreditCardsDTO1).isNotEqualTo(cardTypeCreditCardsDTO2);
    }
}
