package com.ct_ecommerce.eshop.Product.Fridge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Integer> {
    @Query("SELECT f FROM Fridge f WHERE f.title = :title AND f.category = 'Fridge'")
    Optional<Fridge> findFridgeByTitleAndCategory(@Param("title") String title);

}

