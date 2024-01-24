package com.ct_ecommerce.eshop.Product.MobilePhones;

import com.ct_ecommerce.eshop.Product.Fridge.Fridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MobilePhonesService {

    private final MobilePhonesRepository MobilePhonesRepository;

    @Autowired
    public MobilePhonesService(MobilePhonesRepository MobilePhonesRepo) {
        MobilePhonesRepository = MobilePhonesRepo;
    }
    public List<MobilePhones> getAll(){
        try {
            List<MobilePhones> mobilePhones = MobilePhonesRepository.findAll();

            if (mobilePhones.isEmpty()) {
                throw new IllegalStateException("No products (mobile phones) found");
            }

            return mobilePhones;
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
    public void store(MobilePhones phone){
        try{
            Optional<MobilePhones> phoneExists = MobilePhonesRepository.findPhoneByModel(phone.getModel());

            if(phoneExists.isPresent()){
                throw new IllegalStateException("Phone already exists");
            }
            phone.setCategory("Mobile Phone");
            phone.setCreated_at(LocalDateTime.now());
            phone.setUpdated_at(LocalDateTime.now());

            //save mobile phone and variants
            MobilePhonesRepository.save(phone);
        } catch (IllegalStateException ex) {
            //catch threw error
            System.out.println(ex.getMessage());
        }catch (Exception ex){
            //general error
            System.out.println(ex.getMessage());
        }
    }

    public void update(int id, MobilePhones phone){
        try{
            System.out.println("service update...");
            //check if phone exists
            Optional<MobilePhones> phoneExists = MobilePhonesRepository.findById(id);
            if (phoneExists.isEmpty()) {
                throw new IllegalStateException("Mobile phone does not exist");
            }
            //get existing phone
            MobilePhones existingPhone = phoneExists.get();
            //set new values
            existingPhone.setTitle(phone.getTitle());
            existingPhone.setDescription(phone.getDescription());
            existingPhone.setDiscount(phone.getDiscount());
            existingPhone.setStock(phone.getStock());
            existingPhone.setBrand(phone.getBrand());
            existingPhone.setOperatingSystem(phone.getOperatingSystem());
            existingPhone.setModel(phone.getModel());
            existingPhone.setBoxContents(phone.getBoxContents());
            existingPhone.setProcessor(phone.getProcessor());
            existingPhone.setProcessorSpeed(phone.getProcessorSpeed());
            existingPhone.setRamSize(phone.getRamSize());
            existingPhone.setScreenSize(phone.getScreenSize());
            existingPhone.setScreenResolution(phone.getScreenResolution());
            existingPhone.setScreenRefreshRate(phone.getScreenRefreshRate());
            existingPhone.setBatterySize(phone.getBatterySize());
            //set update timestamp
            existingPhone.setUpdated_at(LocalDateTime.now());
            MobilePhonesRepository.save(existingPhone);
        } catch (IllegalStateException ex) {
            //catch threw error
            System.out.println(ex.getMessage());
        } catch (Exception ex){
            //general error
            System.out.println(ex.getMessage());
        }
    }

    public void destroy(int id){
        System.out.println("destroy service..");
        try{
            //check if phone exists
            boolean phoneExists = MobilePhonesRepository.existsById(id);
            if (!phoneExists) {
                throw new IllegalStateException("Mobile phone does not exist");
            }
            MobilePhonesRepository.deleteById(id);
        } catch (IllegalStateException ex) {
            //catch threw error
            System.out.println(ex.getMessage());
        } catch (Exception ex){
            //general error
            System.out.println(ex.getMessage());
        }
    }
}
