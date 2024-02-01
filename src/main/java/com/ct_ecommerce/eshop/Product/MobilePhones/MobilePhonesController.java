package com.ct_ecommerce.eshop.Product.MobilePhones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for all requests for products in mobile phone category
 * @Variable MobilePhoneService ** service layer for mobile phones.
 * @RequestMapping("/api/products/mobile") ** route prefix
 */
@RestController
@RequestMapping("/api/products/mobile")
public class MobilePhonesController {

    private final MobilePhonesService MobilePhoneService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public MobilePhonesController(MobilePhonesService mobilePhoneService){
        this.MobilePhoneService = mobilePhoneService;
    }

    /**
     * Get all mobile phones
     * @GetMapping ** = get request.
     */
    @GetMapping
    public List<MobilePhones> getProducts(){
        MobilePhones discountOfficer = new MobilePhones();

        return (List<MobilePhones>) discountOfficer.calculateDiscounts(MobilePhoneService.getAll());
    }

    /**
     * Create new mobile phone
     * @PostMapping ** = post request.
     * @RequestBody ** = body of the request
     */
    @PostMapping(path = "/create")
    public void store(@RequestBody MobilePhones mobilePhone) {
        //store nre mobile object
        MobilePhoneService.store(mobilePhone);
    }

    /**
     * Update mobile phone by id
     * @PatchMapping ** = update request.
     * @PatchMapping ** annotation
     * @PathVariable ** name of variable in url
     * @RequestBody ** = body of the request
     */
    @PatchMapping(path = "/{phoneID}")
    public void updateStudent(@PathVariable("phoneID") int id, @RequestBody MobilePhones phone){
        //validation
        System.out.println("controller update");
        MobilePhoneService.update(id, phone);
    }

    /**
     * Delete mobile phone by id
     * @PatchMapping ** = delete request.
     * @PathVariable ** name of variable in url
     */
    @DeleteMapping(path = "/{phoneID}")
    public void destroy(@PathVariable("phoneID") int id){
        MobilePhoneService.destroy(id);
    }
}
