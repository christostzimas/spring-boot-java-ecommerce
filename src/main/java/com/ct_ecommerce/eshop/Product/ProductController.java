package com.ct_ecommerce.eshop.Product;

import com.ct_ecommerce.eshop.ResponseServices.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
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
     * @Errors IllegalStateException, Exception
     * @Returns http response
     */
    @GetMapping
    public ResponseEntity getProducts(){
        try{

            return ResponseService.SuccessResponse(productService.getProducts());

        } catch (IllegalStateException ex){
            return ResponseService.BadRequestResponse("No product found");
        } catch (Exception ex) {
            return ResponseService.BadRequestResponse("Error getting all the products");
        }
    }

    /**
     * Create new product
     * @PostMapping ** = post request.
     * @param product (RequestBody) The product
     * @Errors IllegalStateException, RuntimeException
     * @Returns http response
     */
    @PostMapping(path = "/create")
    public ResponseEntity store(@RequestBody Product product){
        try{
            productService.addNewProduct(product);

            return ResponseService.SuccessResponse();
        } catch (IllegalStateException ex){
            return ResponseService.BadRequestResponse("Product already exists");
        } catch (IllegalArgumentException ex) {
            return ResponseService.BadRequestResponse("Can not save empty object");
        } catch(OptimisticLockingFailureException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch(Exception ex){
            return ResponseService.BadRequestResponse("Error storing product");
        }
    }

    /**
     * Update product
     * @PatchMapping ** = update request.
     * @param id (PathVariable) The product id
     * @param product (RequestBody) The product
     * @Errors IllegalStateException, IllegalArgumentException, OptimisticLockingFailureException, Exception
     * @Returns http response
     */
    @PatchMapping(path = "/{productID}")
    public ResponseEntity updateStudent(@PathVariable("productID") int id, @RequestBody  Product product){
        //validation
        try{
            productService.updateProduct(id, product);

            return ResponseService.SuccessResponse();
        }catch (IllegalStateException ex){
            return ResponseService.BadRequestResponse("Product does not exist");
        } catch (IllegalArgumentException ex) {
            return ResponseService.BadRequestResponse("Can not save empty object");
        } catch(OptimisticLockingFailureException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch(Exception ex){
            return ResponseService.BadRequestResponse("Error storing product");
        }
    }

    /**
     * Delete product
     * @DeleteMapping ** = delete request.
     * @param id (PathVariable) The product id
     * @Errors IllegalArgumentException, IllegalStateException, Exception
     * @Returns http response
     */
    @DeleteMapping(path = "/{productID}")
    public ResponseEntity destroy(@PathVariable("productID") int id){
        //force not null
        try{
            productService.deleteProduct(id);

            return ResponseService.SuccessResponse();
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse("Can not perform delete action on null id");
        } catch (IllegalStateException ex) {
            return ResponseService.BadRequestResponse("Product does not exist");
        } catch(Exception ex){
            //general error
            throw new RuntimeException("Error deleting product");
        }
    }

    /**
     * Get specific number of best-selling products
     * @GetMapping ** = get request.
     * @param number (PathVariable) The number of best selling products
     * @Returns http response
     */
    @GetMapping(path = "/bestSellers/{num}")
    public List<Product> getBestSellers(@PathVariable("num") int number){
        try {
            return productService.getBestSellers(number);
        } catch (IllegalStateException ex){
            throw new RuntimeException("No product found");
        } catch (Exception ex) {
            throw new RuntimeException("Error getting best-selling products", ex);
        }

    }
}
