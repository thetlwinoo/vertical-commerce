package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CitiesLocalizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitiesLocalizeDTO.class);
        CitiesLocalizeDTO citiesLocalizeDTO1 = new CitiesLocalizeDTO();
        citiesLocalizeDTO1.setId(1L);
        CitiesLocalizeDTO citiesLocalizeDTO2 = new CitiesLocalizeDTO();
        assertThat(citiesLocalizeDTO1).isNotEqualTo(citiesLocalizeDTO2);
        citiesLocalizeDTO2.setId(citiesLocalizeDTO1.getId());
        assertThat(citiesLocalizeDTO1).isEqualTo(citiesLocalizeDTO2);
        citiesLocalizeDTO2.setId(2L);
        assertThat(citiesLocalizeDTO1).isNotEqualTo(citiesLocalizeDTO2);
        citiesLocalizeDTO1.setId(null);
        assertThat(citiesLocalizeDTO1).isNotEqualTo(citiesLocalizeDTO2);
    }
}
