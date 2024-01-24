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
        try {
            List<Fridge> products = FridgeRepository.findAll();

            if (products.isEmpty()) {
                throw new IllegalStateException("No products found");
            }

            return products;
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch(Exception ex){
            //general error
            System.out.println(ex.getMessage());
        }
        //return proper response
        return null;
    }

    @Transactional
    public void addNewProduct(Fridge fridge) {
        try{
            //check if fridge already exists based on title and category
            Optional<Fridge> fridgeByTitleExists = FridgeRepository.findFridgeByTitleAndCategory(fridge.getTitle());

            if(fridgeByTitleExists.isPresent()){
                throw new IllegalStateException("Fridge already exists");
            }
            //set timestamps and category
            fridge.setCreated_at(LocalDateTime.now());
            fridge.setUpdated_at(LocalDateTime.now());
            fridge.setCategory("Fridge");

            //save fridge
            FridgeRepository.save(fridge);
        }catch(IllegalStateException ex){
            //catch threw error
            System.out.println(ex.getMessage());
        }catch(Exception ex){
            //general error
            System.out.println(ex.getMessage());
        }
    }

    //function to update current fridge
    public void update(int id, Fridge updatedFridge) {
        try{
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

            FridgeRepository.save(existingFridge);
        }catch(IllegalStateException ex){
            //catch threw error
            System.out.println(ex.getMessage());
        }catch(Exception ex){
            //general error
            System.out.println(ex.getMessage());
        }
    }

    public void deleteProduct(int id){
        try{
            boolean exists = FridgeRepository.existsById(id);
            if(!exists){
                throw new IllegalStateException("Product does not exist");
            }
            FridgeRepository.deleteById(id);
        }catch (IllegalStateException ex){
            //catch threw error
            System.out.println(ex.getMessage());
        }catch (Exception ex){
            //general error
            System.out.println(ex.getMessage());
        }
    }
}
