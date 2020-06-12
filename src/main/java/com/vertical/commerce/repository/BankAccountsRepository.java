package com.vertical.commerce.repository;

import com.vertical.commerce.domain.BankAccounts;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BankAccounts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankAccountsRepository extends JpaRepository<BankAccounts, Long>, JpaSpecificationExecutor<BankAccounts> {
}
