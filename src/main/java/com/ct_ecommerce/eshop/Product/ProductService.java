package com.ct_ecommerce.eshop.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    }

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

    public List<Product> getBestSellers(int numOfProducts){
        Sort sortBySalesDesc = Sort.by(Sort.Direction.DESC, "sales");
        Pageable pageable = PageRequest.of(0, numOfProducts, sortBySalesDesc);

        return ProductRepository.findAll(pageable).getContent();
    }
}
