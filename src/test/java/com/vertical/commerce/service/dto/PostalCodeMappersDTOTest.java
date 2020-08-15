package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class PostalCodeMappersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostalCodeMappersDTO.class);
        PostalCodeMappersDTO postalCodeMappersDTO1 = new PostalCodeMappersDTO();
        postalCodeMappersDTO1.setId(1L);
        PostalCodeMappersDTO postalCodeMappersDTO2 = new PostalCodeMappersDTO();
        assertThat(postalCodeMappersDTO1).isNotEqualTo(postalCodeMappersDTO2);
        postalCodeMappersDTO2.setId(postalCodeMappersDTO1.getId());
        assertThat(postalCodeMappersDTO1).isEqualTo(postalCodeMappersDTO2);
        postalCodeMappersDTO2.setId(2L);
        assertThat(postalCodeMappersDTO1).isNotEqualTo(postalCodeMappersDTO2);
        postalCodeMappersDTO1.setId(null);
        assertThat(postalCodeMappersDTO1).isNotEqualTo(postalCodeMappersDTO2);
    }
}
