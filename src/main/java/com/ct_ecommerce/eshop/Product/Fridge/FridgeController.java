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
        }catch (Exception ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Create new product in fridge category
     * @PostMapping ** = post request.
     * @RequestBody ** = body of the request
     * @Returns http response
     */
    @PostMapping(path = "/create")
    public ResponseEntity store(@RequestBody Fridge fridge){
        //validation
        try {
            fridgeService.addNewProduct(fridge);

            return ResponseService.SuccessResponse();
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Update fridge and super object
     * @PatchMapping ** = update request.
     * @PatchMapping ** annotation
     * @PathVariable ** name of variable in url
     * @RequestBody ** = body of the request
     * @Returns http response
     */
    @PatchMapping(path = "/{fridgeID}")
    public ResponseEntity updateFridge(@PathVariable("fridgeID") int id, @RequestBody Fridge fridge){
        //validation
        try {
            fridgeService.update(id, fridge);

            return ResponseService.SuccessResponse();
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Delete fridge and super object
     * @PatchMapping ** = delete request.
     * @PathVariable ** name of variable in url
     */
    @DeleteMapping(path = "/{fridgeID}")
    public ResponseEntity destroy(@PathVariable("fridgeID") int id){
        //force not null
        try{
            fridgeService.deleteProduct(id);

            return ResponseService.SuccessResponse();
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }
}
