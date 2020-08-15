package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TownshipsLocalizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TownshipsLocalizeDTO.class);
        TownshipsLocalizeDTO townshipsLocalizeDTO1 = new TownshipsLocalizeDTO();
        townshipsLocalizeDTO1.setId(1L);
        TownshipsLocalizeDTO townshipsLocalizeDTO2 = new TownshipsLocalizeDTO();
        assertThat(townshipsLocalizeDTO1).isNotEqualTo(townshipsLocalizeDTO2);
        townshipsLocalizeDTO2.setId(townshipsLocalizeDTO1.getId());
        assertThat(townshipsLocalizeDTO1).isEqualTo(townshipsLocalizeDTO2);
        townshipsLocalizeDTO2.setId(2L);
        assertThat(townshipsLocalizeDTO1).isNotEqualTo(townshipsLocalizeDTO2);
        townshipsLocalizeDTO1.setId(null);
        assertThat(townshipsLocalizeDTO1).isNotEqualTo(townshipsLocalizeDTO2);
    }
}
