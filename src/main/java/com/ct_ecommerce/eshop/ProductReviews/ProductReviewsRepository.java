package com.ct_ecommerce.eshop.ProductReviews;

import com.ct_ecommerce.eshop.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewsRepository extends JpaRepository<ProductReviews, Integer> {
    @Query("SELECT reviews FROM ProductReviews reviews WHERE reviews.product_id=:product")
    List<ProductReviews> findProductById(@Param("product")Product product);
}
