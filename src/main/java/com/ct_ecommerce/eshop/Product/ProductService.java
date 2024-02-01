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
        try{
            return ProductRepository.findAll();
        }catch (IllegalStateException e) {
            throw new IllegalArgumentException("No products found");
        } catch(Exception ex){
            //general error
            throw new RuntimeException("Error getting all the products", ex);
        }
    }

    /**
     * Add new product
     * @param product ** product object
     * @Errors ** IllegalStateException if product exists
     */
    public void addNewProduct(Product product) {
        try {
            Optional<Product> productByTitleExists = ProductRepository.findProductByTitle(product.getTitle());
            if (productByTitleExists.isPresent()) {
                throw new IllegalStateException("Product already exists");
                //check also for category
            }
            product.setSales(0);
            product.setUpdated_at(LocalDateTime.now());
            product.setCreated_at(LocalDateTime.now());
            ProductRepository.save(product);
        }catch (IllegalStateException e) {
            throw new IllegalArgumentException("Product already exists");
        } catch(Exception ex){
            //general error
            throw new RuntimeException("Error saving products -exists", ex);
        }
    }

    /**
     * Delete existing product by id
     * @param id ** id of saved fridge
     * @Errors ** IllegalStateException if the product does not exist
     */
    public void deleteProduct(int id) {
        try {
            boolean exists = ProductRepository.existsById(id);
            if (!exists) {
                throw new IllegalStateException("Product does not exist");
            }

            ProductRepository.deleteById(id);
        }catch (IllegalStateException e) {
            throw new IllegalArgumentException("Product  does not exist --delete");
        } catch(Exception ex){
            //general error
            throw new RuntimeException("Error deleting product", ex);
        }
    }

    /**
     * Update existing product by id
     * @param id ** id of saved product
     * @param product ** updated data
     * @Errors ** IllegalStateException if the product does not exist
     */
    public void updateProduct(int id, Product product) {
        try {
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
        }catch (IllegalStateException e) {
            throw new IllegalArgumentException("Product  does not exist --update");
        } catch(Exception ex){
            //general error
            throw new RuntimeException("Error updating product", ex);
        }
    }

    /**
     * Get specific number of best-selling products
     * @param numOfProducts ** count of best-selling products
     * @return  ** return list of products
     */
    public List<Product> getBestSellers(int numOfProducts){
        try{
            Sort sortBySalesDesc = Sort.by(Sort.Direction.DESC, "sales");
            Pageable pageable = PageRequest.of(0, numOfProducts, sortBySalesDesc);

            return ProductRepository.findAll(pageable).getContent();
        }catch (Exception ex) {
            throw new RuntimeException("Error getting best-selling products", ex);
        }
    }
}
