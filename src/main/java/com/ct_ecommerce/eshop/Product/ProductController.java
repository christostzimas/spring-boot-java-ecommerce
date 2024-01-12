package com.ct_ecommerce.eshop.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")   //route prefix
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    //list all products
    @GetMapping(path = "/")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    //create new product
    @PostMapping(path = "/create")
    public void store(@RequestBody Product product){
        System.out.println(product);
        productService.addNewProduct(product);
    }

    //update product
    @PatchMapping(path = "/{productID}")
    public void updateStudent(@PathVariable("productID") int id, @RequestBody  Product product){
        //validation
        productService.updateProduct(id, product);
    }

    //delete product
    @DeleteMapping(path = "/{productID}")
    public void destroy(@PathVariable("productID") int id){
        //force not null
        productService.deleteProduct(id);
    }

    //get best selling broducts
    @GetMapping(path = "/bestSellers/{num}")
    public List<Product> getBestSellers(@PathVariable("num") int number){
        return productService.getBestSellers(number);
    }
}
