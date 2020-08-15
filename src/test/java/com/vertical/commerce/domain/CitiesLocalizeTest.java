package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CitiesLocalizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitiesLocalize.class);
        CitiesLocalize citiesLocalize1 = new CitiesLocalize();
        citiesLocalize1.setId(1L);
        CitiesLocalize citiesLocalize2 = new CitiesLocalize();
        citiesLocalize2.setId(citiesLocalize1.getId());
        assertThat(citiesLocalize1).isEqualTo(citiesLocalize2);
        citiesLocalize2.setId(2L);
        assertThat(citiesLocalize1).isNotEqualTo(citiesLocalize2);
        citiesLocalize1.setId(null);
        assertThat(citiesLocalize1).isNotEqualTo(citiesLocalize2);
    }
}
