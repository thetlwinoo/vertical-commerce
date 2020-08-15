package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class StateProvincesLocalizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateProvincesLocalizeDTO.class);
        StateProvincesLocalizeDTO stateProvincesLocalizeDTO1 = new StateProvincesLocalizeDTO();
        stateProvincesLocalizeDTO1.setId(1L);
        StateProvincesLocalizeDTO stateProvincesLocalizeDTO2 = new StateProvincesLocalizeDTO();
        assertThat(stateProvincesLocalizeDTO1).isNotEqualTo(stateProvincesLocalizeDTO2);
        stateProvincesLocalizeDTO2.setId(stateProvincesLocalizeDTO1.getId());
        assertThat(stateProvincesLocalizeDTO1).isEqualTo(stateProvincesLocalizeDTO2);
        stateProvincesLocalizeDTO2.setId(2L);
        assertThat(stateProvincesLocalizeDTO1).isNotEqualTo(stateProvincesLocalizeDTO2);
        stateProvincesLocalizeDTO1.setId(null);
        assertThat(stateProvincesLocalizeDTO1).isNotEqualTo(stateProvincesLocalizeDTO2);
    }
}
