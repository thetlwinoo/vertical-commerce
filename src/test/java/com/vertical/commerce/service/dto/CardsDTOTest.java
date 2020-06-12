package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CardsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardsDTO.class);
        CardsDTO cardsDTO1 = new CardsDTO();
        cardsDTO1.setId(1L);
        CardsDTO cardsDTO2 = new CardsDTO();
        assertThat(cardsDTO1).isNotEqualTo(cardsDTO2);
        cardsDTO2.setId(cardsDTO1.getId());
        assertThat(cardsDTO1).isEqualTo(cardsDTO2);
        cardsDTO2.setId(2L);
        assertThat(cardsDTO1).isNotEqualTo(cardsDTO2);
        cardsDTO1.setId(null);
        assertThat(cardsDTO1).isNotEqualTo(cardsDTO2);
    }
}
