package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class DiscountTypesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountTypesDTO.class);
        DiscountTypesDTO discountTypesDTO1 = new DiscountTypesDTO();
        discountTypesDTO1.setId(1L);
        DiscountTypesDTO discountTypesDTO2 = new DiscountTypesDTO();
        assertThat(discountTypesDTO1).isNotEqualTo(discountTypesDTO2);
        discountTypesDTO2.setId(discountTypesDTO1.getId());
        assertThat(discountTypesDTO1).isEqualTo(discountTypesDTO2);
        discountTypesDTO2.setId(2L);
        assertThat(discountTypesDTO1).isNotEqualTo(discountTypesDTO2);
        discountTypesDTO1.setId(null);
        assertThat(discountTypesDTO1).isNotEqualTo(discountTypesDTO2);
    }
}
