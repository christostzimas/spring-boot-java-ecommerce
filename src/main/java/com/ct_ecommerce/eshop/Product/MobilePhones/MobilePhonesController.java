package com.ct_ecommerce.eshop.Product.MobilePhones;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.AppUsers.AppUserService;
import com.ct_ecommerce.eshop.ResponseServices.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for all requests for products in mobile phone category
 * @Variable MobilePhoneService ** service layer for mobile phones.
 * @Variable ResponseService ** service used to pass http responses to client
 * @Variable AppUserService ** used to check if user is admin
 * @RequestMapping("/api/products/mobile") ** route prefix
 */
@RestController
@RequestMapping("/api/products/mobile")
public class MobilePhonesController {

    private final MobilePhonesService MobilePhoneService;
    private final ResponseService ResponseService;
    private final AppUserService AppUserService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public MobilePhonesController(MobilePhonesService mobilePhoneService, ResponseService ResponseService, AppUserService appUserService){
        this.MobilePhoneService = mobilePhoneService;
        this.ResponseService = ResponseService;
        this.AppUserService = appUserService;
    }

    /**
     * Get all mobile phones
     * @GetMapping ** = get request.
     * @Errors IllegalStateException, IllegalArgumentException, Exception
     * @Returns http response
     */
    @GetMapping
    public ResponseEntity getProducts(){
        try {
            MobilePhones discountOfficer = new MobilePhones();

            return ResponseService.SuccessResponse((List<MobilePhones>) discountOfficer.calculateDiscounts(MobilePhoneService.getAll()));
        } catch(IllegalArgumentException ex){
            return ResponseService.BadRequestResponse("Error getting products");
        } catch (IllegalStateException ex){
            return ResponseService.BadRequestResponse("No products (mobile phones) found");
        } catch (Exception ex) {
            return ResponseService.BadRequestResponse("Error getting all the mobile phones");
        }
    }

    /**
     * Create new mobile phone
     * @PostMapping ** = post request.
     * @param mobilePhone The mobile phone object (body of the request)
     * @Errors IllegalStateException, IllegalArgumentException, OptimisticLockingFailureException, Exception
     * @Returns http response
     */
    @PostMapping(path = "/create")
    public ResponseEntity store(@AuthenticationPrincipal AppUser user, @RequestBody MobilePhones mobilePhone) {
        //store nre mobile object
        try {
            /** Check if user is the administrator */
            if(!AppUserService.isUserAdmin(user)){
                return ResponseService.BadRequestResponse("Not allowed");
            }

            MobilePhoneService.store(mobilePhone);

            return ResponseService.SuccessResponse();
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse("Can not save empty object");
        } catch (OptimisticLockingFailureException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (IllegalStateException ex) {
            return ResponseService.BadRequestResponse("Phone already exists");
        } catch (Exception ex){
            throw new RuntimeException("Error storing new mobile phone");
        }
    }

    /**
     * Update mobile phone by id
     * @PatchMapping ** = update request.
     * @PatchMapping ** annotation
     * @param id (PathVariable) The id of mobile phone.
     * @param phone (RequestBody) The mobile phone object
     * @Errors IllegalStateException, IllegalArgumentException, OptimisticLockingFailureException, Exception
     * @Returns http response
     */
    @PatchMapping(path = "/{phoneID}")
    public ResponseEntity updateStudent(@AuthenticationPrincipal AppUser user, @PathVariable("phoneID") int id, @RequestBody MobilePhones phone){
        //validation
        try {
            /** Check if user is the administrator */
            if(!AppUserService.isUserAdmin(user)){
                return ResponseService.BadRequestResponse("Not allowed");
            }

            MobilePhoneService.update(id, phone);

            return ResponseService.SuccessResponse();
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse("Can not update empty object");
        } catch (OptimisticLockingFailureException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (IllegalStateException ex) {
            return ResponseService.BadRequestResponse("Mobile phone does not exist");
        } catch (Exception ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Delete mobile phone by id
     * @DeleteMapping ** = delete request.
     * @param id (PathVariable) The id of mobile phone
     * @Errors IllegalStateException, IllegalArgumentException, Exception
     * @Returns http response
     */
    @DeleteMapping(path = "/{phoneID}")
    public ResponseEntity destroy(@AuthenticationPrincipal AppUser user, @PathVariable("phoneID") int id){
        try {
            /** Check if user is the administrator */
            if(!AppUserService.isUserAdmin(user)){
                return ResponseService.BadRequestResponse("Not allowed");
            }

            MobilePhoneService.destroy(id);

            return ResponseService.SuccessResponse();
        } catch (IllegalStateException ex) {
            return ResponseService.BadRequestResponse("Mobile phone does not exist");
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse("Id is not provided");
        } catch (Exception ex) {
            return ResponseService.BadRequestResponse("Error deleting mobile phone");
        }
    }
}
