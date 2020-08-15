package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class PostalCodeMappersLocalizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostalCodeMappersLocalize.class);
        PostalCodeMappersLocalize postalCodeMappersLocalize1 = new PostalCodeMappersLocalize();
        postalCodeMappersLocalize1.setId(1L);
        PostalCodeMappersLocalize postalCodeMappersLocalize2 = new PostalCodeMappersLocalize();
        postalCodeMappersLocalize2.setId(postalCodeMappersLocalize1.getId());
        assertThat(postalCodeMappersLocalize1).isEqualTo(postalCodeMappersLocalize2);
        postalCodeMappersLocalize2.setId(2L);
        assertThat(postalCodeMappersLocalize1).isNotEqualTo(postalCodeMappersLocalize2);
        postalCodeMappersLocalize1.setId(null);
        assertThat(postalCodeMappersLocalize1).isNotEqualTo(postalCodeMappersLocalize2);
    }
}
