package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TownshipsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Townships.class);
        Townships townships1 = new Townships();
        townships1.setId(1L);
        Townships townships2 = new Townships();
        townships2.setId(townships1.getId());
        assertThat(townships1).isEqualTo(townships2);
        townships2.setId(2L);
        assertThat(townships1).isNotEqualTo(townships2);
        townships1.setId(null);
        assertThat(townships1).isNotEqualTo(townships2);
    }
}
