package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CountriesLocalizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountriesLocalize.class);
        CountriesLocalize countriesLocalize1 = new CountriesLocalize();
        countriesLocalize1.setId(1L);
        CountriesLocalize countriesLocalize2 = new CountriesLocalize();
        countriesLocalize2.setId(countriesLocalize1.getId());
        assertThat(countriesLocalize1).isEqualTo(countriesLocalize2);
        countriesLocalize2.setId(2L);
        assertThat(countriesLocalize1).isNotEqualTo(countriesLocalize2);
        countriesLocalize1.setId(null);
        assertThat(countriesLocalize1).isNotEqualTo(countriesLocalize2);
    }
}
