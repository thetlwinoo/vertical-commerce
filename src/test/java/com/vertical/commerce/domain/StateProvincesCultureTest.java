package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class StateProvincesCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateProvincesCulture.class);
        StateProvincesCulture stateProvincesCulture1 = new StateProvincesCulture();
        stateProvincesCulture1.setId(1L);
        StateProvincesCulture stateProvincesCulture2 = new StateProvincesCulture();
        stateProvincesCulture2.setId(stateProvincesCulture1.getId());
        assertThat(stateProvincesCulture1).isEqualTo(stateProvincesCulture2);
        stateProvincesCulture2.setId(2L);
        assertThat(stateProvincesCulture1).isNotEqualTo(stateProvincesCulture2);
        stateProvincesCulture1.setId(null);
        assertThat(stateProvincesCulture1).isNotEqualTo(stateProvincesCulture2);
    }
}
