package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Suppliers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Suppliers entity.
 */
@Repository
public interface SuppliersRepository extends JpaRepository<Suppliers, Long>, JpaSpecificationExecutor<Suppliers> {

    @Query(value = "select distinct suppliers from Suppliers suppliers left join fetch suppliers.deliveryMethods",
        countQuery = "select count(distinct suppliers) from Suppliers suppliers")
    Page<Suppliers> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct suppliers from Suppliers suppliers left join fetch suppliers.deliveryMethods")
    List<Suppliers> findAllWithEagerRelationships();

    @Query("select suppliers from Suppliers suppliers left join fetch suppliers.deliveryMethods where suppliers.id =:id")
    Optional<Suppliers> findOneWithEagerRelationships(@Param("id") Long id);
}
