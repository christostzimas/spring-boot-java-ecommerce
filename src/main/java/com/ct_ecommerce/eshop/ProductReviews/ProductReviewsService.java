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
     */
    public List<ProductReviews> getAllByProductID(Product product) {
        try{
            return ProductReviewsRepository.findProductById(product);
        } catch (Exception ex){
            throw new RuntimeException("error retrieving product reviews " + ex.getMessage());
        }
    }

    /**
     * Save new product review
     * @param review The review object
     */
    public void saveReview(ProductReviews review) {
        try{
            ProductReviewsRepository.save(review);
        } catch (IllegalArgumentException ex){
            /** In case object is null */
            System.out.println(ex.getMessage());
            throw new RuntimeException("Object cannot be empty ");

        } catch (OptimisticLockingFailureException ex){
            System.out.println(ex.getMessage());
            throw new RuntimeException("Error storing review..");
        }
    }
}
