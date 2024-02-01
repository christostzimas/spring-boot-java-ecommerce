package com.ct_ecommerce.eshop.Product.MobilePhones;

import com.ct_ecommerce.eshop.ResponseServices.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for all requests for products in mobile phone category
 * @Variable MobilePhoneService ** service layer for mobile phones.
 * @Variable ResponseService ** service used to pass http responses to client
 * @RequestMapping("/api/products/mobile") ** route prefix
 */
@RestController
@RequestMapping("/api/products/mobile")
public class MobilePhonesController {

    private final MobilePhonesService MobilePhoneService;
    private final ResponseService ResponseService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public MobilePhonesController(MobilePhonesService mobilePhoneService, ResponseService ResponseService){
        this.MobilePhoneService = mobilePhoneService;
        this.ResponseService = ResponseService;
    }

    /**
     * Get all mobile phones
     * @GetMapping ** = get request.
     * @Returns http response
     */
    @GetMapping
    public ResponseEntity getProducts(){
        try {
            MobilePhones discountOfficer = new MobilePhones();

            return ResponseService.SuccessResponse((List<MobilePhones>) discountOfficer.calculateDiscounts(MobilePhoneService.getAll()));
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Create new mobile phone
     * @PostMapping ** = post request.
     * @RequestBody ** = body of the request
     * @Returns http response
     */
    @PostMapping(path = "/create")
    public ResponseEntity store(@RequestBody MobilePhones mobilePhone) {
        //store nre mobile object
        try {
            MobilePhoneService.store(mobilePhone);

            return ResponseService.SuccessResponse();
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Update mobile phone by id
     * @PatchMapping ** = update request.
     * @PatchMapping ** annotation
     * @PathVariable ** name of variable in url
     * @RequestBody ** = body of the request
     * @Returns http response
     */
    @PatchMapping(path = "/{phoneID}")
    public ResponseEntity updateStudent(@PathVariable("phoneID") int id, @RequestBody MobilePhones phone){
        //validation
        try {
            MobilePhoneService.update(id, phone);

            return ResponseService.SuccessResponse();
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Delete mobile phone by id
     * @PatchMapping ** = delete request.
     * @PathVariable ** name of variable in url
     * @Returns http response
     */
    @DeleteMapping(path = "/{phoneID}")
    public ResponseEntity destroy(@PathVariable("phoneID") int id){
        try {
            MobilePhoneService.destroy(id);

            return ResponseService.SuccessResponse();
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }
}
