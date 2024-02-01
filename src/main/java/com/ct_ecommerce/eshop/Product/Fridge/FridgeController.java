package com.ct_ecommerce.eshop.Product.Fridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for all requests for products in fridge category
 * @Variable fridgeService ** service layer for fridges.
 * @RequestMapping("/api/products/fridge") ** route prefix
 */
@RestController
@RequestMapping("/api/products/fridge")   //route prefix
public class FridgeController {
    private final FridgeService fridgeService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public FridgeController(FridgeService fridgeService){
        this.fridgeService = fridgeService;
    }

    /**
     * Get all products from fridge category
     * @GetMapping ** = get request.
     */
    @GetMapping
    public List<Fridge> getProducts(){
        Fridge discountOfficer = new Fridge();

        return (List<Fridge>) discountOfficer.calculateDiscounts(fridgeService.getProducts());
    }

    /**
     * Create new product in fridge category
     * @PostMapping ** = post request.
     * @RequestBody ** = body of the request
     */
    @PostMapping(path = "/create")
    public void store(@RequestBody Fridge fridge){
        //validation
        fridgeService.addNewProduct(fridge);
    }

    /**
     * Update fridge and super object
     * @PatchMapping ** = update request.
     * @PatchMapping ** annotation
     * @PathVariable ** name of variable in url
     * @RequestBody ** = body of the request
     */
    @PatchMapping(path = "/{fridgeID}")
    public void updateFridge(@PathVariable("fridgeID") int id, @RequestBody Fridge fridge){
        //validation
        fridgeService.update(id, fridge);
    }

    /**
     * Delete fridge and super object
     * @PatchMapping ** = delete request.
     * @PathVariable ** name of variable in url
     */
    @DeleteMapping(path = "/{fridgeID}")
    public void destroy(@PathVariable("fridgeID") int id){
        //force not null
        fridgeService.deleteProduct(id);
    }
}
