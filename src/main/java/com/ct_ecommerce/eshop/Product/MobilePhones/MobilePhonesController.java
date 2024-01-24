package com.ct_ecommerce.eshop.Product.MobilePhones;

import com.ct_ecommerce.eshop.Product.Product;
import com.ct_ecommerce.eshop.Product.ProductController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products/mobile")
public class MobilePhonesController {

    private final MobilePhonesService MobilePhoneService;

    @Autowired
    public MobilePhonesController(MobilePhonesService mobilePhoneService){
        this.MobilePhoneService = mobilePhoneService;
    }

    //get all mobile phones
    @GetMapping
    public List<MobilePhones> getProducts(){
        MobilePhones discountOfficer = new MobilePhones();

        return (List<MobilePhones>) discountOfficer.calculateDiscounts(MobilePhoneService.getAll());
    }

    //create new mobile phone
    @PostMapping(path = "/create")
    public void store(@RequestBody MobilePhones mobilePhone) {
        //store nre mobile object
        MobilePhoneService.store(mobilePhone);
    }

    //update mobile phone
    @PatchMapping(path = "/{phoneID}")
    public void updateStudent(@PathVariable("phoneID") int id, @RequestBody MobilePhones phone){
        //validation
        System.out.println("controller update");
        MobilePhoneService.update(id, phone);
    }

    //delete phone by id
    @DeleteMapping(path = "/{phoneID}")
    public void destroy(@PathVariable("phoneID") int id){
        MobilePhoneService.destroy(id);
    }
}
