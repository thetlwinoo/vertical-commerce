package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TownshipsCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TownshipsCultureDTO.class);
        TownshipsCultureDTO townshipsCultureDTO1 = new TownshipsCultureDTO();
        townshipsCultureDTO1.setId(1L);
        TownshipsCultureDTO townshipsCultureDTO2 = new TownshipsCultureDTO();
        assertThat(townshipsCultureDTO1).isNotEqualTo(townshipsCultureDTO2);
        townshipsCultureDTO2.setId(townshipsCultureDTO1.getId());
        assertThat(townshipsCultureDTO1).isEqualTo(townshipsCultureDTO2);
        townshipsCultureDTO2.setId(2L);
        assertThat(townshipsCultureDTO1).isNotEqualTo(townshipsCultureDTO2);
        townshipsCultureDTO1.setId(null);
        assertThat(townshipsCultureDTO1).isNotEqualTo(townshipsCultureDTO2);
    }
}
