package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CountriesCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountriesCultureDTO.class);
        CountriesCultureDTO countriesCultureDTO1 = new CountriesCultureDTO();
        countriesCultureDTO1.setId(1L);
        CountriesCultureDTO countriesCultureDTO2 = new CountriesCultureDTO();
        assertThat(countriesCultureDTO1).isNotEqualTo(countriesCultureDTO2);
        countriesCultureDTO2.setId(countriesCultureDTO1.getId());
        assertThat(countriesCultureDTO1).isEqualTo(countriesCultureDTO2);
        countriesCultureDTO2.setId(2L);
        assertThat(countriesCultureDTO1).isNotEqualTo(countriesCultureDTO2);
        countriesCultureDTO1.setId(null);
        assertThat(countriesCultureDTO1).isNotEqualTo(countriesCultureDTO2);
    }
}
