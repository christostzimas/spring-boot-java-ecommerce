package com.ct_ecommerce.eshop.Product.Fridge;

import com.ct_ecommerce.eshop.ResponseServices.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for all requests for products in fridge category
 * @Variable fridgeService ** service layer for fridges.
 * @Variable ResponseService ** service used to pass http responses to client
 * @RequestMapping("/api/products/fridge") ** route prefix
 */
@RestController
@RequestMapping("/api/products/fridge")
public class FridgeController {
    private final FridgeService fridgeService;
    private final ResponseService ResponseService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public FridgeController(FridgeService fridgeService, ResponseService ResponseService){
        this.fridgeService = fridgeService;
        this.ResponseService = ResponseService;
    }

    /**
     * Get all products from fridge category
     * @GetMapping ** = get request.
     * @Errors IllegalStateException, RuntimeException
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
     * @Returns http response
     */
    @PostMapping(path = "/create")
    public ResponseEntity store(@RequestBody Fridge fridge){
        //validation
        try {
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
     * @Returns http response
     */
    @PatchMapping(path = "/{fridgeID}")
    public ResponseEntity updateFridge(@PathVariable("fridgeID") int id, @RequestBody Fridge fridge){
        //validation
        try {
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
     */
    @DeleteMapping(path = "/{fridgeID}")
    public ResponseEntity destroy(@PathVariable("fridgeID") int id){
        //force not null
        try{
            fridgeService.deleteProduct(id);

            return ResponseService.SuccessResponse();
        } catch (IllegalStateException ex){
            return ResponseService.BadRequestResponse("Product does not exist");
        } catch (Exception ex) {
            return ResponseService.BadRequestResponse("Error deleting fridge by id..");
        }
    }
}
