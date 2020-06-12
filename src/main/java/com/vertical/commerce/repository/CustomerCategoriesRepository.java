package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CustomerCategories;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCategoriesRepository extends JpaRepository<CustomerCategories, Long>, JpaSpecificationExecutor<CustomerCategories> {
}
