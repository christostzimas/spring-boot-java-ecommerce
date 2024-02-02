package com.ct_ecommerce.eshop.OrderQuantities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderQuantitiesRepository extends JpaRepository<OrderQuantities, Integer> {
}
