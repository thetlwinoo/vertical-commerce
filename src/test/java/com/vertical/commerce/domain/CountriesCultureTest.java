package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CountriesCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountriesCulture.class);
        CountriesCulture countriesCulture1 = new CountriesCulture();
        countriesCulture1.setId(1L);
        CountriesCulture countriesCulture2 = new CountriesCulture();
        countriesCulture2.setId(countriesCulture1.getId());
        assertThat(countriesCulture1).isEqualTo(countriesCulture2);
        countriesCulture2.setId(2L);
        assertThat(countriesCulture1).isNotEqualTo(countriesCulture2);
        countriesCulture1.setId(null);
        assertThat(countriesCulture1).isNotEqualTo(countriesCulture2);
    }
}
