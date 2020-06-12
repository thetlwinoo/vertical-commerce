package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class DiscountDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountDetailsDTO.class);
        DiscountDetailsDTO discountDetailsDTO1 = new DiscountDetailsDTO();
        discountDetailsDTO1.setId(1L);
        DiscountDetailsDTO discountDetailsDTO2 = new DiscountDetailsDTO();
        assertThat(discountDetailsDTO1).isNotEqualTo(discountDetailsDTO2);
        discountDetailsDTO2.setId(discountDetailsDTO1.getId());
        assertThat(discountDetailsDTO1).isEqualTo(discountDetailsDTO2);
        discountDetailsDTO2.setId(2L);
        assertThat(discountDetailsDTO1).isNotEqualTo(discountDetailsDTO2);
        discountDetailsDTO1.setId(null);
        assertThat(discountDetailsDTO1).isNotEqualTo(discountDetailsDTO2);
    }
}
