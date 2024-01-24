package com.ct_ecommerce.eshop.Product.Fridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/fridge")   //route prefix
public class FridgeController {
    private final FridgeService fridgeService;

    @Autowired
    public FridgeController(FridgeService fridgeService){
        this.fridgeService = fridgeService;
    }

    //get all products from fridge category
    @GetMapping
    public List<Fridge> getProducts(){
        Fridge discountOfficer = new Fridge();

        return (List<Fridge>) discountOfficer.calculateDiscounts(fridgeService.getProducts());
    }

    //create new fridge
    @PostMapping(path = "/create")
    public void store(@RequestBody Fridge fridge){
        //validation
        fridgeService.addNewProduct(fridge);
    }

    //update fridge and super object
    @PatchMapping(path = "/{fridgeID}")
    public void updateFridge(@PathVariable("fridgeID") int id, @RequestBody Fridge fridge){
        //validation
        fridgeService.update(id, fridge);
    }

    //delete fridge and super object
    @DeleteMapping(path = "/{fridgeID}")
    public void destroy(@PathVariable("fridgeID") int id){
        //force not null
        fridgeService.deleteProduct(id);
    }
}
