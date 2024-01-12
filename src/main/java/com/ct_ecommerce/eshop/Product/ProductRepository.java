package com.ct_ecommerce.eshop.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    //SELECT * FROM PRODUCTS WHERE title = ?
    //@Query("SELECT p FROM Products p WHERE p.title = ?1")
    Optional<Product> findProductByTitle(String title);

    List<Product> findAllByCategory(String category);
}
