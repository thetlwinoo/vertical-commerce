package com.vertical.commerce.repository;

import com.vertical.commerce.domain.SpecialDeals;

public interface SpecialDealsExtendRepository extends SpecialDealsRepository {
    SpecialDeals findSpecialDealsByDiscountCode(String code);
}
