package com.ct_ecommerce.eshop.Product.Fridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for products in fridge category
 * @Variable FridgeRepository ** repository layer products in fridges category.
 */
@Service
public class FridgeService {
    private final FridgeRepository FridgeRepository;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     * @param fridgeRepository ** used for db actions
     */
    @Autowired
    public FridgeService(FridgeRepository fridgeRepository) {
        FridgeRepository = fridgeRepository;
    }

    /**
     * Get all products in fridge category
     * @Errors ** IllegalStateException if no products found
     * @Returns List of products in fridge category
     */
    public List<Fridge> getProducts(){

        List<Fridge> products = FridgeRepository.findAll();

        if (products.isEmpty()) {
            throw new IllegalStateException();
        }
        return products;
    }

    /**
     * Add new product in fridge category
     * @param fridge ** fridge object contenting super for save
     * @Errors ** IllegalStateException if product exists + general
     */
    @Transactional
    public void addNewProduct(Fridge fridge) {

        /** check if fridge already exists based on title and category */
        Optional<Fridge> fridgeByTitleExists = FridgeRepository.findFridgeByTitleAndCategory(fridge.getTitle());

        if(fridgeByTitleExists.isPresent()){
            throw new IllegalStateException();
        }
        /** set timestamps and category */
        fridge.setCreated_at(LocalDateTime.now());
        fridge.setUpdated_at(LocalDateTime.now());
        fridge.setCategory("Fridge");

        /** save fridge */
        FridgeRepository.save(fridge);
    }

    /**
     * Update existing fridge by id
     * @param id ** id of saved fridge
     * @param updatedFridge ** updated data
     * @Errors ** IllegalStateException if product does not exist + general
     */
    public void update(int id, Fridge updatedFridge) {

        Fridge existingFridge = FridgeRepository.getReferenceById(id);

        if(existingFridge == null){
            throw new IllegalStateException("Fridge does not exist");
        }

        /** update superclass fields */
        existingFridge.setTitle(updatedFridge.getTitle());
        existingFridge.setDescription(updatedFridge.getDescription());
        existingFridge.setPrice(updatedFridge.getPrice());
        existingFridge.setDiscount(updatedFridge.getDiscount());
        existingFridge.setStock(updatedFridge.getStock());
        existingFridge.setBrand(updatedFridge.getBrand());
        existingFridge.setUpdated_at(LocalDateTime.now());

        /** update fridge fields */
        existingFridge.setLtCapacity(updatedFridge.getLtCapacity());
        existingFridge.setCoolingType(updatedFridge.getCoolingType());
        existingFridge.setDimensions(updatedFridge.getDimensions());
        existingFridge.setAnnualEnergyConsumption(updatedFridge.getAnnualEnergyConsumption());
        existingFridge.setEnergyClass(updatedFridge.getEnergyClass());
        existingFridge.setNoiseEnergyClass(updatedFridge.getNoiseEnergyClass());

        /** update fridge */
        FridgeRepository.save(existingFridge);
    }

    /**
     * Delete existing fridge by id
     * @param id ** id of saved fridge
     * @Errors ** IllegalStateException if the user does not exist + general
     */
    public void deleteProduct(int id){
        boolean exists = FridgeRepository.existsById(id);

        if(!exists){
            throw new IllegalStateException();
        }

        FridgeRepository.deleteById(id);

    }
}
