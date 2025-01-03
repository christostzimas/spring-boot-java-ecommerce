package com.ct_ecommerce.eshop.Product;

import com.ct_ecommerce.eshop.dto.UnavailableProductsDTO;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.*;

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
     * @Returns List of all products
     */
    public List<Product> getProducts(){
        List<Product> products = ProductRepository.findAll();

        if(products.isEmpty()){
            throw new IllegalStateException();
        }

        return products;
    }

    /**
     * Add new product
     * @param product ** product object
     * @Errors ** IllegalStateException if product exists
     */
    public void addNewProduct(Product product) {
        /** Check if product exists */
        Optional<Product> productByTitleExists = ProductRepository.findProductByTitle(product.getTitle());

        if (productByTitleExists.isPresent()) {
            throw new IllegalStateException();
            //check also for category
        }

        /** set sales & timestamps */
        product.setSales(0);
        product.setUpdated_at(LocalDateTime.now());
        product.setCreated_at(LocalDateTime.now());

        ProductRepository.save(product);
    }

    /**
     * Get existing product by id
     * @param id The product id.
     * @Errors ** IllegalStateException, EntityExistsException
     */
    public Product getProductByID(int id) {

        /** Check if product exists */
        boolean exists = ProductRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException();
        }

        return ProductRepository.getReferenceById(id);
    }

    /**
     * Delete existing product by id
     * @param id ** id of saved fridge
     * @Errors ** IllegalStateException if the product does not exist
     */
    public void deleteProduct(int id) {
        /** Check if product exists */
        boolean exists = ProductRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException();
        }

        ProductRepository.deleteById(id);
    }

    /**
     * Update existing product by id
     * @param id ** id of saved product
     * @param product ** updated data
     * @Errors ** IllegalStateException
     */
    public void updateProduct(int id, Product product) {
        /** Check if product exists */
        Product existingProduct = ProductRepository.getReferenceById(id);

        if(existingProduct == null){
            throw new IllegalStateException();
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

    /**
     * Update stock of product with id
     * @param productId ** id of product
     * @param quantityToSubtract ** quantity to subtract from stock
     * @Errors RuntimeException
     */
    public void updateProductStock(int productId, int quantityToSubtract){
        try{
            Optional<Product> optionalProduct = ProductRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                if(product.getStock() < quantityToSubtract){
                    throw new RuntimeException("Product stock is not enough for this purchase");
                }
                product.setStock(product.getStock() - quantityToSubtract);
                ProductRepository.save(product);
            }
        } catch (Exception ex){
            throw new RuntimeException("error updating product quantity", ex);
        }
    }

    /**
     * Update stock of multiple products
     * @param updatedValuesWithIds ** HashMap with productId as key and quantity to subtract as value
     * @Errors RuntimeException
     */
    public void updateProductStockFromList(Map<Integer, Integer> updatedValuesWithIds){
        try{
            for (Map.Entry<Integer, Integer> entry : updatedValuesWithIds.entrySet()) {
                this.updateProductStock(entry.getKey(), entry.getValue());
            }
        } catch (Exception ex){
            throw new RuntimeException("error updating product quantity", ex);
        }
    }

    /**
     * Return a List of type Product with all the ordered products
     * @param *Set<Integer> ids * collection with unique integers (ids of products)
     * @return list of type Product
     * @Errors RuntimeException
     */
    public List<Product> getAllOrderedProducts(Set<Integer> ids){
        try{
            return ProductRepository.findAllById(ids);
        } catch (Exception ex){
            throw new RuntimeException("error getting specific products");
        }
    }

    /**
     * Return a List of type UnavailableProductsDTO with all the ordered products with lower stock
     * @param  *List<Product> products list of ordered products
     * @param  *Map<Integer, Integer> productQuantities Hashmap of ids as keys and quantity as value
     * @return list of type UnavailableProductsDTO -- if list not empty some products don't have enough stock
     */
    public List<UnavailableProductsDTO> checkProductsAvaliability(List<Product> products, Map<Integer, Integer> productQuantities){
        List<UnavailableProductsDTO> unavailableProductsList = new ArrayList<>();
        for(Product product: products){
            if(product.getStock() < productQuantities.get(product.getId())){
                unavailableProductsList.add(new UnavailableProductsDTO(product.getId(), product.getTitle(), product.getStock()));
            }
        }

        return unavailableProductsList;
    }
}
