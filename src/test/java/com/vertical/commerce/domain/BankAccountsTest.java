package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class BankAccountsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankAccounts.class);
        BankAccounts bankAccounts1 = new BankAccounts();
        bankAccounts1.setId(1L);
        BankAccounts bankAccounts2 = new BankAccounts();
        bankAccounts2.setId(bankAccounts1.getId());
        assertThat(bankAccounts1).isEqualTo(bankAccounts2);
        bankAccounts2.setId(2L);
        assertThat(bankAccounts1).isNotEqualTo(bankAccounts2);
        bankAccounts1.setId(null);
        assertThat(bankAccounts1).isNotEqualTo(bankAccounts2);
    }
}
