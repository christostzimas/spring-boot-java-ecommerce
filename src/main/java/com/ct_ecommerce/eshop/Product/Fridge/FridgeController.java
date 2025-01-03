package com.ct_ecommerce.eshop.Product.Fridge;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.AppUsers.AppUserService;
import com.ct_ecommerce.eshop.ResponseServices.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for all requests for products in fridge category
 * @Variable fridgeService ** service layer for fridges.
 * @Variable ResponseService ** service used to pass http responses to client
 * @Variable AppUserService ** used to check if user is admin
 * @RequestMapping("/api/products/fridge") ** route prefix
 */
@RestController
@RequestMapping("/api/products/fridge")
public class FridgeController {
    private final FridgeService fridgeService;
    private final ResponseService ResponseService;
    private final AppUserService AppUserService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public FridgeController(FridgeService fridgeService, ResponseService ResponseService,AppUserService appUserService){
        this.fridgeService = fridgeService;
        this.ResponseService = ResponseService;
        this.AppUserService = appUserService;
    }

    /**
     * Get all products from fridge category
     * @GetMapping ** = get request.
     * @Errors IllegalStateException, Exception
     * @Returns http response
     */
    @GetMapping
    public ResponseEntity<Fridge> getProducts(){
        try {
            Fridge discountOfficer = new Fridge();
            List<Fridge> allFridges = (List<Fridge>) discountOfficer.calculateDiscounts(fridgeService.getProducts());

            return ResponseService.SuccessResponse(allFridges);
        } catch (IllegalStateException e) {

            return ResponseService.BadRequestResponse("No products found in fridge category");
        } catch(Exception ex){

            //general error
            return ResponseService.BadRequestResponse("Error getting all the fridges");
        }
    }

    /**
     * Create new product in fridge category
     * @PostMapping ** = post request.
     * @RequestBody ** = body of the request
     * @param fridge The new fridge object
     * @Errors ** IllegalStateException, Exception
     * @Returns http response
     */
    @PostMapping(path = "/create")
    public ResponseEntity store(@AuthenticationPrincipal AppUser user, @RequestBody Fridge fridge){
        //validation
        try {
            /** Check if user is the administrator */
            if(!AppUserService.isUserAdmin(user)){
                return ResponseService.BadRequestResponse("Not allowed");
            }

            fridgeService.addNewProduct(fridge);

            return ResponseService.SuccessResponse();
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse("Fridge already exists");
        } catch (Exception ex) {
            return ResponseService.BadRequestResponse("Error storing new fridges");
        }
    }

    /**
     * Update fridge and super object
     * @PatchMapping ** = update request.
     * @PatchMapping ** annotation
     * @param id PathVariable (fridgeID) The id of fridge for update
     * @param fridge The updated fridge product
     * @Errors ** IllegalStateException, Exception
     * @Returns http response
     */
    @PatchMapping(path = "/{fridgeID}")
    public ResponseEntity updateFridge(@AuthenticationPrincipal AppUser user, @PathVariable("fridgeID") int id, @RequestBody Fridge fridge){
        //validation
        try {
            /** Check if user is the administrator */
            if(!AppUserService.isUserAdmin(user)){
                return ResponseService.BadRequestResponse("Not allowed");
            }

            fridgeService.update(id, fridge);

            return ResponseService.SuccessResponse();
        } catch (IllegalStateException ex){
            return ResponseService.BadRequestResponse("Fridge does not exist");
        } catch (Exception ex) {
            return ResponseService.BadRequestResponse("Error updating fridge by id..");
        }
    }

    /**
     * Delete fridge and super object
     * @PatchMapping ** = delete request.
     * @param id PathVariable (fridgeID) The id of fridge
     * @Errors ** IllegalStateException, Exception
     */
    @DeleteMapping(path = "/{fridgeID}")
    public ResponseEntity destroy(@AuthenticationPrincipal AppUser user, @PathVariable("fridgeID") int id){
        //force not null
        try{
            /** Check if user is the administrator */
            if(!AppUserService.isUserAdmin(user)){
                return ResponseService.BadRequestResponse("Not allowed");
            }

            fridgeService.deleteProduct(id);

            return ResponseService.SuccessResponse();
        } catch (IllegalStateException ex){
            return ResponseService.BadRequestResponse("Product does not exist");
        } catch (Exception ex) {
            return ResponseService.BadRequestResponse("Error deleting fridge by id..");
        }
    }
}
