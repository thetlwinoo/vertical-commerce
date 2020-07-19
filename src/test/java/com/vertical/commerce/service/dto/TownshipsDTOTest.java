package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TownshipsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TownshipsDTO.class);
        TownshipsDTO townshipsDTO1 = new TownshipsDTO();
        townshipsDTO1.setId(1L);
        TownshipsDTO townshipsDTO2 = new TownshipsDTO();
        assertThat(townshipsDTO1).isNotEqualTo(townshipsDTO2);
        townshipsDTO2.setId(townshipsDTO1.getId());
        assertThat(townshipsDTO1).isEqualTo(townshipsDTO2);
        townshipsDTO2.setId(2L);
        assertThat(townshipsDTO1).isNotEqualTo(townshipsDTO2);
        townshipsDTO1.setId(null);
        assertThat(townshipsDTO1).isNotEqualTo(townshipsDTO2);
    }
}
