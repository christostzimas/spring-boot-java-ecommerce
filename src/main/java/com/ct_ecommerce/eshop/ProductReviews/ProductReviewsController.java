package com.ct_ecommerce.eshop.ProductReviews;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.Product.Product;
import com.ct_ecommerce.eshop.Product.ProductService;
import com.ct_ecommerce.eshop.ResponseServices.ResponseService;
import com.ct_ecommerce.eshop.SuccessfulOrder.SuccessfulOrderService;
import com.ct_ecommerce.eshop.dto.OrderReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for all requests related to successful orders
 * @Variable ProductReviewsService ** service layer for reviews.
 * @Variable SuccessfulOrderService ** service layer for successful orders.
 * @Variable SuccessfulOrderService ** service layer for successful orders.
 * @Variable ResponseService ** service used to pass http responses to client
 * @RequestMapping("/api/products/orders") ** route prefix
 */

@RestController
@RequestMapping("/api/products/reviews")
public class ProductReviewsController {
    private final ProductReviewsService ProductReviewsService;
    private final ResponseService ResponseService;
    private final ProductService ProductService;
    private final SuccessfulOrderService SuccessfulOrderService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public ProductReviewsController(ProductReviewsService productReviewsService, ResponseService ResponseService, ProductService productService, SuccessfulOrderService successfulOrderService){
        this.ProductReviewsService = productReviewsService;
        this.ResponseService = ResponseService;
        this.ProductService = productService;
        this.SuccessfulOrderService = successfulOrderService;
    }

    /**
     * Get all reviews for specific product
     * @GetMapping ** = get request.
     * @Param user ** The authenticated user
     * @param productID the product id
     * @Errors RuntimeException
     * @Returns http response
     */
    @GetMapping("/{productID}")
    public ResponseEntity<ProductReviews> getProductReviews(@AuthenticationPrincipal AppUser user, @PathVariable("productID") int productID){
        try{
            /** Get product if exists */
            Product product = ProductService.getProductByID(productID);

            List<ProductReviews> orders = ProductReviewsService.getAllByProductID(product);

            return ResponseService.SuccessResponse(orders);
        } catch (RuntimeException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Post new review
     * @PostMapping ** = post request.
     * @Param user ** The authenticated user
     * @Param orderReviewDTO ** orderReviewDTO object containing the order number
     * @Errors Exception
     * @Returns http response
     */
    @PostMapping("/create")
    public ResponseEntity addNewReview(@AuthenticationPrincipal AppUser user, @RequestBody OrderReviewDTO orderReviewDTO){

        try {
            /** Get product if exists */
            Product product = ProductService.getProductByID(orderReviewDTO.getProductId());

            /** check if user has bought the product */
            boolean hasUserBoughtProduct = SuccessfulOrderService.hasUserPurchasedProduct(user, product.getId());

            if(!hasUserBoughtProduct){
                throw new RuntimeException("Can not submit review because you haven't bought or received this product yet");
            }

            /** create new object */
            ProductReviews review = new ProductReviews(
                    orderReviewDTO.getStartRating(),
                    product,
                    user,
                    orderReviewDTO.getComment()
            );

            ProductReviewsService.saveReview(review);

            return ResponseService.SuccessResponse();
        } catch (RuntimeException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }
}
