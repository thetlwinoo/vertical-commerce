package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class StateProvincesLocalizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateProvincesLocalize.class);
        StateProvincesLocalize stateProvincesLocalize1 = new StateProvincesLocalize();
        stateProvincesLocalize1.setId(1L);
        StateProvincesLocalize stateProvincesLocalize2 = new StateProvincesLocalize();
        stateProvincesLocalize2.setId(stateProvincesLocalize1.getId());
        assertThat(stateProvincesLocalize1).isEqualTo(stateProvincesLocalize2);
        stateProvincesLocalize2.setId(2L);
        assertThat(stateProvincesLocalize1).isNotEqualTo(stateProvincesLocalize2);
        stateProvincesLocalize1.setId(null);
        assertThat(stateProvincesLocalize1).isNotEqualTo(stateProvincesLocalize2);
    }
}
