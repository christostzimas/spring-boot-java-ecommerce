package com.ct_ecommerce.eshop.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for all requests for products
 * @Variable productService ** service layer for products.
 * @RequestMapping("/api/products") ** route prefix
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    /**
     * Get all products
     * @GetMapping ** = get request.
     */
    @GetMapping
    public List<Product> getProducts(){
        //need to get specific from categories??
        return productService.getProducts();
    }

    /**
     * Create new product
     * @PostMapping ** = post request.
     * @RequestBody ** = body of the request
     */
    @PostMapping(path = "/create")
    public void store(@RequestBody Product product){
        System.out.println(product);
        productService.addNewProduct(product);
    }

    /**
     * Update product
     * @PatchMapping ** = update request.
     * @PatchMapping ** annotation
     * @PathVariable ** name of variable in url
     * @RequestBody ** = body of the request
     */
    @PatchMapping(path = "/{productID}")
    public void updateStudent(@PathVariable("productID") int id, @RequestBody  Product product){
        //validation
        productService.updateProduct(id, product);
    }

    /**
     * Delete product
     * @PatchMapping ** = delete request.
     * @PathVariable ** name of variable in url
     */
    @DeleteMapping(path = "/{productID}")
    public void destroy(@PathVariable("productID") int id){
        //force not null
        productService.deleteProduct(id);
    }

    /**
     * Get specific number of best-selling products
     * @GetMapping ** = get request.
     * @PathVariable ** name of variable in url
     */
    @GetMapping(path = "/bestSellers/{num}")
    public List<Product> getBestSellers(@PathVariable("num") int number){
        return productService.getBestSellers(number);
    }
}
