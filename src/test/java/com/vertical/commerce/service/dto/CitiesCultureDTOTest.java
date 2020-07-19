package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CitiesCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitiesCultureDTO.class);
        CitiesCultureDTO citiesCultureDTO1 = new CitiesCultureDTO();
        citiesCultureDTO1.setId(1L);
        CitiesCultureDTO citiesCultureDTO2 = new CitiesCultureDTO();
        assertThat(citiesCultureDTO1).isNotEqualTo(citiesCultureDTO2);
        citiesCultureDTO2.setId(citiesCultureDTO1.getId());
        assertThat(citiesCultureDTO1).isEqualTo(citiesCultureDTO2);
        citiesCultureDTO2.setId(2L);
        assertThat(citiesCultureDTO1).isNotEqualTo(citiesCultureDTO2);
        citiesCultureDTO1.setId(null);
        assertThat(citiesCultureDTO1).isNotEqualTo(citiesCultureDTO2);
    }
}
