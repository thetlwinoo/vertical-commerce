package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CountriesLocalizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountriesLocalizeDTO.class);
        CountriesLocalizeDTO countriesLocalizeDTO1 = new CountriesLocalizeDTO();
        countriesLocalizeDTO1.setId(1L);
        CountriesLocalizeDTO countriesLocalizeDTO2 = new CountriesLocalizeDTO();
        assertThat(countriesLocalizeDTO1).isNotEqualTo(countriesLocalizeDTO2);
        countriesLocalizeDTO2.setId(countriesLocalizeDTO1.getId());
        assertThat(countriesLocalizeDTO1).isEqualTo(countriesLocalizeDTO2);
        countriesLocalizeDTO2.setId(2L);
        assertThat(countriesLocalizeDTO1).isNotEqualTo(countriesLocalizeDTO2);
        countriesLocalizeDTO1.setId(null);
        assertThat(countriesLocalizeDTO1).isNotEqualTo(countriesLocalizeDTO2);
    }
}
