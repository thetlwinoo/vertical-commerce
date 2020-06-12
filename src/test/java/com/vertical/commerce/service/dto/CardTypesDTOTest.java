package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CardTypesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardTypesDTO.class);
        CardTypesDTO cardTypesDTO1 = new CardTypesDTO();
        cardTypesDTO1.setId(1L);
        CardTypesDTO cardTypesDTO2 = new CardTypesDTO();
        assertThat(cardTypesDTO1).isNotEqualTo(cardTypesDTO2);
        cardTypesDTO2.setId(cardTypesDTO1.getId());
        assertThat(cardTypesDTO1).isEqualTo(cardTypesDTO2);
        cardTypesDTO2.setId(2L);
        assertThat(cardTypesDTO1).isNotEqualTo(cardTypesDTO2);
        cardTypesDTO1.setId(null);
        assertThat(cardTypesDTO1).isNotEqualTo(cardTypesDTO2);
    }
}
