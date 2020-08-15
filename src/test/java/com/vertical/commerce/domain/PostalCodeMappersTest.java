package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class PostalCodeMappersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostalCodeMappers.class);
        PostalCodeMappers postalCodeMappers1 = new PostalCodeMappers();
        postalCodeMappers1.setId(1L);
        PostalCodeMappers postalCodeMappers2 = new PostalCodeMappers();
        postalCodeMappers2.setId(postalCodeMappers1.getId());
        assertThat(postalCodeMappers1).isEqualTo(postalCodeMappers2);
        postalCodeMappers2.setId(2L);
        assertThat(postalCodeMappers1).isNotEqualTo(postalCodeMappers2);
        postalCodeMappers1.setId(null);
        assertThat(postalCodeMappers1).isNotEqualTo(postalCodeMappers2);
    }
}
