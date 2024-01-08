package com.ct_ecommerce.eshop.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping(path = "products")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @PostMapping(path = "products/create")
    public void store(@RequestBody Product product){
        System.out.println(product);
        productService.addNewProduct(product);
    }

    @DeleteMapping(path = "products/{productID}")
    public void destroy(@PathVariable("productID") int id){
        //force not null
        productService.deleteProduct(id);
    }

    @PatchMapping(path = "products/{productID}")
    public void updateStudent(@PathVariable("productID") int id, @RequestBody  Product product){
        System.out.println(id);
        System.out.println("controller....");
        productService.updateProduct(id, product);
    }
}
