package com.ct_ecommerce.eshop.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for products
 * @Variable ProductRepository ** repository layer products.
 */
@Service
public class ProductService {

    private final ProductRepository ProductRepository;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     * @param productRepository ** used for db actions
     */
    @Autowired
    public ProductService(ProductRepository productRepository) {
        ProductRepository = productRepository;
    }

    /**
     * Get all products
     */
    public List<Product> getProducts(){
        return ProductRepository.findAll();
    }

    /**
     * Add new product
     * @param product ** product object
     * @Errors ** IllegalStateException if product exists
     */
    public void addNewProduct(Product product) {
        Optional<Product> productByTitleExists = ProductRepository.findProductByTitle(product.getTitle());
        if(productByTitleExists.isPresent()){
            throw new IllegalStateException("Product already exists");
            //check also for category
        }
        product.setSales(0);
        product.setUpdated_at(LocalDateTime.now());
        product.setCreated_at(LocalDateTime.now());
        ProductRepository.save(product);
    }

    /**
     * Delete existing product by id
     * @param id ** id of saved fridge
     * @Errors ** IllegalStateException if the product does not exist
     */
    public void deleteProduct(int id) {
        boolean exists = ProductRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Product does not exist");
        }

        ProductRepository.deleteById(id);
    }

    /**
     * Update existing product by id
     * @param id ** id of saved product
     * @param product ** updated data
     * @Errors ** IllegalStateException if the product does not exist
     */
    public void updateProduct(int id, Product product) {
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

    /**
     * Get specific number of best-selling products
     * @param numOfProducts ** count of best-selling products
     * @return  ** return list of products
     */
    public List<Product> getBestSellers(int numOfProducts){
        Sort sortBySalesDesc = Sort.by(Sort.Direction.DESC, "sales");
        Pageable pageable = PageRequest.of(0, numOfProducts, sortBySalesDesc);

        return ProductRepository.findAll(pageable).getContent();
    }
}
