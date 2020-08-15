package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TownsLocalizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TownsLocalizeDTO.class);
        TownsLocalizeDTO townsLocalizeDTO1 = new TownsLocalizeDTO();
        townsLocalizeDTO1.setId(1L);
        TownsLocalizeDTO townsLocalizeDTO2 = new TownsLocalizeDTO();
        assertThat(townsLocalizeDTO1).isNotEqualTo(townsLocalizeDTO2);
        townsLocalizeDTO2.setId(townsLocalizeDTO1.getId());
        assertThat(townsLocalizeDTO1).isEqualTo(townsLocalizeDTO2);
        townsLocalizeDTO2.setId(2L);
        assertThat(townsLocalizeDTO1).isNotEqualTo(townsLocalizeDTO2);
        townsLocalizeDTO1.setId(null);
        assertThat(townsLocalizeDTO1).isNotEqualTo(townsLocalizeDTO2);
    }
}
