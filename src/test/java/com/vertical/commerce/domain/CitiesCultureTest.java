package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CitiesCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitiesCulture.class);
        CitiesCulture citiesCulture1 = new CitiesCulture();
        citiesCulture1.setId(1L);
        CitiesCulture citiesCulture2 = new CitiesCulture();
        citiesCulture2.setId(citiesCulture1.getId());
        assertThat(citiesCulture1).isEqualTo(citiesCulture2);
        citiesCulture2.setId(2L);
        assertThat(citiesCulture1).isNotEqualTo(citiesCulture2);
        citiesCulture1.setId(null);
        assertThat(citiesCulture1).isNotEqualTo(citiesCulture2);
    }
}
