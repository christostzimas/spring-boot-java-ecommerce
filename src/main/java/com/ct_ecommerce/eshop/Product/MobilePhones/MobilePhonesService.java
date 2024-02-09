package com.ct_ecommerce.eshop.Product.MobilePhones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for products in mobile phone category
 * @Variable MobilePhonesRepository ** repository layer products in mobile phones category.
 */
@Service
public class MobilePhonesService {

    private final MobilePhonesRepository MobilePhonesRepository;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     * @param MobilePhonesRepo ** used for db actions
     */
    @Autowired
    public MobilePhonesService(MobilePhonesRepository MobilePhonesRepo) {
        MobilePhonesRepository = MobilePhonesRepo;
    }

    /**
     * Get all products in mobile phones category
     * @Errors ** IllegalStateException if no products found
     * @returns List with all products in fridge category.
     */
    public List<MobilePhones> getAll(){

        List<MobilePhones> mobilePhones = MobilePhonesRepository.findAll();

        if (mobilePhones.isEmpty()) {
            throw new IllegalStateException();
        }

        return mobilePhones;

    }

    /**
     * Add new product in mobile phones category
     * @param phone ** mobile phone object contenting super for save
     * @Errors ** IllegalStateException if product exists + general
     */
    @Transactional
    public void store(MobilePhones phone){
        Optional<MobilePhones> phoneExists = MobilePhonesRepository.findPhoneByModel(phone.getModel());

        if(phoneExists.isPresent()){
            throw new IllegalStateException();
        }

        phone.setCategory("Mobile Phone");
        phone.setCreated_at(LocalDateTime.now());
        phone.setUpdated_at(LocalDateTime.now());

        /** save mobile phone and variants */
        MobilePhonesRepository.save(phone);
    }

    /**
     * Update existing mobile phone by id
     * @param id ** id of saved mobile phone
     * @param phone ** updated data
     * @Errors ** IllegalStateException if product does not exist + general
     */
    public void update(int id, MobilePhones phone){
        /** check if phone exists */
        Optional<MobilePhones> phoneExists = MobilePhonesRepository.findById(id);

        if (phoneExists.isEmpty()) {
            throw new IllegalStateException();
        }

        /** get existing phone */
        MobilePhones existingPhone = phoneExists.get();

        /** set new values */
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

        /** set update timestamps */
        existingPhone.setUpdated_at(LocalDateTime.now());

        /** save mobile phone */
        MobilePhonesRepository.save(existingPhone);
    }

    /**
     * Delete existing mobile phone by id
     * @param id ** id of saved mobile phone
     * @Errors ** IllegalStateException if the user does not exist + general
     */
    public void destroy(int id){
        //check if phone exists
        boolean phoneExists = MobilePhonesRepository.existsById(id);

        if (!phoneExists) {
            throw new IllegalStateException();
        }

        MobilePhonesRepository.deleteById(id);
    }
}
