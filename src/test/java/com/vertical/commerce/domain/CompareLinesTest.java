package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CompareLinesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompareLines.class);
        CompareLines compareLines1 = new CompareLines();
        compareLines1.setId(1L);
        CompareLines compareLines2 = new CompareLines();
        compareLines2.setId(compareLines1.getId());
        assertThat(compareLines1).isEqualTo(compareLines2);
        compareLines2.setId(2L);
        assertThat(compareLines1).isNotEqualTo(compareLines2);
        compareLines1.setId(null);
        assertThat(compareLines1).isNotEqualTo(compareLines2);
    }
}
