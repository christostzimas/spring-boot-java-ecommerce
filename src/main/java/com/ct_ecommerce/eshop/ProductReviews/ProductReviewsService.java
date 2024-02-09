package com.ct_ecommerce.eshop.ProductReviews;

import com.ct_ecommerce.eshop.Product.Product;
import com.ct_ecommerce.eshop.Product.ProductRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReviewsService {
    private final ProductReviewsRepository ProductReviewsRepository;

    /**
     * Constructor
     * @param productReviewsRepository ** used for db actions
     */
    public ProductReviewsService(ProductReviewsRepository productReviewsRepository) {
        this.ProductReviewsRepository = productReviewsRepository;
    }

    /**
     * Get all reviews for a product
     * @param product The product
     * @Errors Exception
     * @Returns Product review
     */
    public List<ProductReviews> getAllByProductID(Product product) {
        return ProductReviewsRepository.findProductById(product);
    }

    /**
     * Save new product review
     * @param review The review object
     * @Errors IllegalArgumentException, OptimisticLockingFailureException
     */
    public void saveReview(ProductReviews review) {
        ProductReviewsRepository.save(review);
    }
}
