package com.ct_ecommerce.eshop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuccessfulOrdersQuantitiesRepository extends JpaRepository<SuccessfulOrdersQuantities, Integer> {
}
