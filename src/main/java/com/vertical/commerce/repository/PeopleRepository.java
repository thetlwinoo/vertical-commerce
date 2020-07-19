package com.vertical.commerce.repository;

import com.vertical.commerce.domain.People;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the People entity.
 */
@Repository
public interface PeopleRepository extends JpaRepository<People, Long>, JpaSpecificationExecutor<People> {

    @Query(value = "select distinct people from People people left join fetch people.suppliers",
        countQuery = "select count(distinct people) from People people")
    Page<People> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct people from People people left join fetch people.suppliers")
    List<People> findAllWithEagerRelationships();

    @Query("select people from People people left join fetch people.suppliers where people.id =:id")
    Optional<People> findOneWithEagerRelationships(@Param("id") Long id);
}
