package com.ct_ecommerce.eshop.Product.Fridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FridgeService {
    private final FridgeRepository FridgeRepository;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository) {
        FridgeRepository = fridgeRepository;
    }
    public List<Fridge> getProducts(){
        List<Fridge> products = FridgeRepository.findAll();

        if (products.isEmpty()) {
            throw new IllegalStateException("No products found");
        }

        return products;
    }

    @Transactional
    public void addNewProduct(Fridge fridge) {
        //check if fridge already exists based on title and category
        Optional<Fridge> fridgeByTitleExists = FridgeRepository.findFridgeByTitleAndCategory(fridge.getTitle());

        if(fridgeByTitleExists.isPresent()){
            throw new IllegalStateException("Fridge already exists");
        }
        //set timestamps and category
        fridge.setCreated_at(LocalDateTime.now());
        fridge.setUpdated_at(LocalDateTime.now());
        fridge.setCategory("Fridge");

        System.out.println(fridge.getDiscount());
        if(fridge.getDiscount() > 0){
            fridge.setDiscountedPrice(fridge.discountPercentage(fridge.getPrice(), fridge.getDiscount()));
        }
        //save fridge
        Fridge savedFridge = FridgeRepository.save(fridge);
        if (savedFridge != null) {
            // Operation completed successfully
        } else {
            // Handle the case where save operation failed
        }
    }

    //function to update current fridge
    public void update(int id, Fridge updatedFridge) {
        Fridge existingFridge = FridgeRepository.getReferenceById(id);
        if(existingFridge == null){
            throw new IllegalStateException("Fridge does not exist");
        }
        //update superclass fields
        existingFridge.setTitle(updatedFridge.getTitle());
        existingFridge.setDescription(updatedFridge.getDescription());
        existingFridge.setPrice(updatedFridge.getPrice());
        existingFridge.setDiscount(updatedFridge.getDiscount());
        existingFridge.setStock(updatedFridge.getStock());
        existingFridge.setBrand(updatedFridge.getBrand());
        existingFridge.setUpdated_at(LocalDateTime.now());
        //update fridge fields
        existingFridge.setLtCapacity(updatedFridge.getLtCapacity());
        existingFridge.setCoolingType(updatedFridge.getCoolingType());
        existingFridge.setDimensions(updatedFridge.getDimensions());
        existingFridge.setAnnualEnergyConsumption(updatedFridge.getAnnualEnergyConsumption());
        existingFridge.setEnergyClass(updatedFridge.getEnergyClass());
        existingFridge.setNoiseEnergyClass(updatedFridge.getNoiseEnergyClass());

        Fridge savedFridge = FridgeRepository.save(existingFridge);
        if (savedFridge != null) {
            // Operation completed successfully
        } else {
            // Handle the case where save operation failed
        }
    }

    public void deleteProduct(int id){
        boolean exists = FridgeRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Product does not exist");
        }

        FridgeRepository.deleteById(id);
    }
}
