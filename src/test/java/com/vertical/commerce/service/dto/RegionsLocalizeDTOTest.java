package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class RegionsLocalizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegionsLocalizeDTO.class);
        RegionsLocalizeDTO regionsLocalizeDTO1 = new RegionsLocalizeDTO();
        regionsLocalizeDTO1.setId(1L);
        RegionsLocalizeDTO regionsLocalizeDTO2 = new RegionsLocalizeDTO();
        assertThat(regionsLocalizeDTO1).isNotEqualTo(regionsLocalizeDTO2);
        regionsLocalizeDTO2.setId(regionsLocalizeDTO1.getId());
        assertThat(regionsLocalizeDTO1).isEqualTo(regionsLocalizeDTO2);
        regionsLocalizeDTO2.setId(2L);
        assertThat(regionsLocalizeDTO1).isNotEqualTo(regionsLocalizeDTO2);
        regionsLocalizeDTO1.setId(null);
        assertThat(regionsLocalizeDTO1).isNotEqualTo(regionsLocalizeDTO2);
    }
}
