package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TownsCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TownsCultureDTO.class);
        TownsCultureDTO townsCultureDTO1 = new TownsCultureDTO();
        townsCultureDTO1.setId(1L);
        TownsCultureDTO townsCultureDTO2 = new TownsCultureDTO();
        assertThat(townsCultureDTO1).isNotEqualTo(townsCultureDTO2);
        townsCultureDTO2.setId(townsCultureDTO1.getId());
        assertThat(townsCultureDTO1).isEqualTo(townsCultureDTO2);
        townsCultureDTO2.setId(2L);
        assertThat(townsCultureDTO1).isNotEqualTo(townsCultureDTO2);
        townsCultureDTO1.setId(null);
        assertThat(townsCultureDTO1).isNotEqualTo(townsCultureDTO2);
    }
}
