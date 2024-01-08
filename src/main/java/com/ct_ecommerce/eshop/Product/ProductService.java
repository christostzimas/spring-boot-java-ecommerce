package com.ct_ecommerce.eshop.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository ProductRepository;

    @Autowired
    public ProductService(com.ct_ecommerce.eshop.Product.ProductRepository productRepository) {
        ProductRepository = productRepository;
    }

    public List<Product> getProducts(){
        return ProductRepository.findAll();
        /*return List.of(
                new Product("test 1", "test test test", 10.5, 0.0, 15, "brand 1", "category 1"),
                new Product("test 2", "test test test", 10.5, 0.0, 15, "brand 2", "category 2")
        );*/
    }

    public void addNewProduct(Product product) {
        Optional<Product> productByTitleExists = ProductRepository.findProductByTitle(product.getTitle());
        if(productByTitleExists.isPresent()){
            throw new IllegalStateException("Product already exists");
            //check also for category
        }
        product.setUpdated_at(LocalDateTime.now());
        product.setCreated_at(LocalDateTime.now());
        ProductRepository.save(product);
    }

    public void deleteProduct(int id) {
        boolean exists = ProductRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Product does not exist");
        }

        ProductRepository.deleteById(id);
    }

    public void updateProduct(int id, Product product) {
        System.out.println("service...");
        Product existingProduct = ProductRepository.getReferenceById(id);
        if(existingProduct == null){
            throw new IllegalStateException("Product does not exist");
        }
        existingProduct.setTitle(product.getTitle());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setStock(product.getStock());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setUpdated_at(LocalDateTime.now());

        ProductRepository.save(existingProduct);
    }
}