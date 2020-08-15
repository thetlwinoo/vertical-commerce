package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class MaterialsLocalizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialsLocalize.class);
        MaterialsLocalize materialsLocalize1 = new MaterialsLocalize();
        materialsLocalize1.setId(1L);
        MaterialsLocalize materialsLocalize2 = new MaterialsLocalize();
        materialsLocalize2.setId(materialsLocalize1.getId());
        assertThat(materialsLocalize1).isEqualTo(materialsLocalize2);
        materialsLocalize2.setId(2L);
        assertThat(materialsLocalize1).isNotEqualTo(materialsLocalize2);
        materialsLocalize1.setId(null);
        assertThat(materialsLocalize1).isNotEqualTo(materialsLocalize2);
    }
}
