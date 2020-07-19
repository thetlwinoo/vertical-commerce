package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TownsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Towns.class);
        Towns towns1 = new Towns();
        towns1.setId(1L);
        Towns towns2 = new Towns();
        towns2.setId(towns1.getId());
        assertThat(towns1).isEqualTo(towns2);
        towns2.setId(2L);
        assertThat(towns1).isNotEqualTo(towns2);
        towns1.setId(null);
        assertThat(towns1).isNotEqualTo(towns2);
    }
}
