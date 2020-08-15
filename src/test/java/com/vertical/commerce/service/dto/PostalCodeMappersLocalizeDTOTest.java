package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class PostalCodeMappersLocalizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostalCodeMappersLocalizeDTO.class);
        PostalCodeMappersLocalizeDTO postalCodeMappersLocalizeDTO1 = new PostalCodeMappersLocalizeDTO();
        postalCodeMappersLocalizeDTO1.setId(1L);
        PostalCodeMappersLocalizeDTO postalCodeMappersLocalizeDTO2 = new PostalCodeMappersLocalizeDTO();
        assertThat(postalCodeMappersLocalizeDTO1).isNotEqualTo(postalCodeMappersLocalizeDTO2);
        postalCodeMappersLocalizeDTO2.setId(postalCodeMappersLocalizeDTO1.getId());
        assertThat(postalCodeMappersLocalizeDTO1).isEqualTo(postalCodeMappersLocalizeDTO2);
        postalCodeMappersLocalizeDTO2.setId(2L);
        assertThat(postalCodeMappersLocalizeDTO1).isNotEqualTo(postalCodeMappersLocalizeDTO2);
        postalCodeMappersLocalizeDTO1.setId(null);
        assertThat(postalCodeMappersLocalizeDTO1).isNotEqualTo(postalCodeMappersLocalizeDTO2);
    }
}
