package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class DiscountsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountsDTO.class);
        DiscountsDTO discountsDTO1 = new DiscountsDTO();
        discountsDTO1.setId(1L);
        DiscountsDTO discountsDTO2 = new DiscountsDTO();
        assertThat(discountsDTO1).isNotEqualTo(discountsDTO2);
        discountsDTO2.setId(discountsDTO1.getId());
        assertThat(discountsDTO1).isEqualTo(discountsDTO2);
        discountsDTO2.setId(2L);
        assertThat(discountsDTO1).isNotEqualTo(discountsDTO2);
        discountsDTO1.setId(null);
        assertThat(discountsDTO1).isNotEqualTo(discountsDTO2);
    }
}
