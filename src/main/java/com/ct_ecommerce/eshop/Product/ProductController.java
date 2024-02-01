package com.ct_ecommerce.eshop.Product;

import com.ct_ecommerce.eshop.ResponseServices.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for all requests for products
 * @Variable productService ** service layer for products.
 * @Variable ResponseService ** service used to pass http responses to client
 * @RequestMapping("/api/products") ** route prefix
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    private final ResponseService ResponseService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public ProductController(ProductService productService, ResponseService ResponseService){
        this.productService = productService;
        this.ResponseService = ResponseService;
    }

    /**
     * Get all products
     * @GetMapping ** = get request.
     * @Errors IllegalStateException, RuntimeException
     * @Returns http response
     */
    @GetMapping
    public ResponseEntity getProducts(){
        //need to get specific from categories??
        try{
            return ResponseService.SuccessResponse(productService.getProducts());
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Create new product
     * @PostMapping ** = post request.
     * @RequestBody ** = body of the request
     * @Errors IllegalStateException, RuntimeException
     * @Returns http response
     */
    @PostMapping(path = "/create")
    public ResponseEntity store(@RequestBody Product product){
        try{
            productService.addNewProduct(product);

            return ResponseService.SuccessResponse();
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }

    }

    /**
     * Update product
     * @PatchMapping ** = update request.
     * @PatchMapping ** annotation
     * @PathVariable ** name of variable in url
     * @RequestBody ** = body of the request
     * @Returns http response
     */
    @PatchMapping(path = "/{productID}")
    public ResponseEntity updateStudent(@PathVariable("productID") int id, @RequestBody  Product product){
        //validation
        try{
            productService.updateProduct(id, product);

            return ResponseService.SuccessResponse();
        }catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Delete product
     * @PatchMapping ** = delete request.
     * @PathVariable ** name of variable in url
     * @Returns http response
     */
    @DeleteMapping(path = "/{productID}")
    public ResponseEntity destroy(@PathVariable("productID") int id){
        //force not null
        try{
            productService.deleteProduct(id);

            return ResponseService.SuccessResponse();
        }catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Get specific number of best-selling products
     * @GetMapping ** = get request.
     * @PathVariable ** name of variable in url
     * @Returns http response
     */
    @GetMapping(path = "/bestSellers/{num}")
    public List<Product> getBestSellers(@PathVariable("num") int number){
        return productService.getBestSellers(number);
    }
}
