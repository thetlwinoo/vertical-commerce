package com.vertical.commerce.repository;

import com.vertical.commerce.domain.People;

import java.util.Optional;

public interface PeopleExtendRepository extends PeopleRepository {
    Optional<People> findPeopleByEmailAddress(String email);

    Optional<People> findPeopleByUserId(String id);
}
