package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class StateProvincesCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateProvincesCultureDTO.class);
        StateProvincesCultureDTO stateProvincesCultureDTO1 = new StateProvincesCultureDTO();
        stateProvincesCultureDTO1.setId(1L);
        StateProvincesCultureDTO stateProvincesCultureDTO2 = new StateProvincesCultureDTO();
        assertThat(stateProvincesCultureDTO1).isNotEqualTo(stateProvincesCultureDTO2);
        stateProvincesCultureDTO2.setId(stateProvincesCultureDTO1.getId());
        assertThat(stateProvincesCultureDTO1).isEqualTo(stateProvincesCultureDTO2);
        stateProvincesCultureDTO2.setId(2L);
        assertThat(stateProvincesCultureDTO1).isNotEqualTo(stateProvincesCultureDTO2);
        stateProvincesCultureDTO1.setId(null);
        assertThat(stateProvincesCultureDTO1).isNotEqualTo(stateProvincesCultureDTO2);
    }
}
