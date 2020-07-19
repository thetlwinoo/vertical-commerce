package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class MaterialsCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialsCulture.class);
        MaterialsCulture materialsCulture1 = new MaterialsCulture();
        materialsCulture1.setId(1L);
        MaterialsCulture materialsCulture2 = new MaterialsCulture();
        materialsCulture2.setId(materialsCulture1.getId());
        assertThat(materialsCulture1).isEqualTo(materialsCulture2);
        materialsCulture2.setId(2L);
        assertThat(materialsCulture1).isNotEqualTo(materialsCulture2);
        materialsCulture1.setId(null);
        assertThat(materialsCulture1).isNotEqualTo(materialsCulture2);
    }
}
