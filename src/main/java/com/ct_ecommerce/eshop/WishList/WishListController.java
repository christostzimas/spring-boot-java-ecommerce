package com.ct_ecommerce.eshop.WishList;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.Product.Product;
import com.ct_ecommerce.eshop.Product.ProductService;
import com.ct_ecommerce.eshop.ResponseServices.ResponseService;
import com.ct_ecommerce.eshop.dto.WishListItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for all requests related to successful orders
 * @Variable WishListService ** service layer for wishlist items.
 * @Variable ProductService Product service
 * @Variable ResponseService ** service used to pass http responses to client
 * @RequestMapping("/api/wishlist") ** route prefix
 */

@RestController
@RequestMapping("/api/wishlist")
public class WishListController {
    private final WishListService WishListService;
    private final ResponseService ResponseService;
    private final ProductService ProductService;

    /**
     * Constructor
     * @Autowired ** config to be autowired by Spring's dependency injection facilities.
     */
    @Autowired
    public WishListController(WishListService WishListService, ResponseService ResponseService, ProductService ProductService){
        this.WishListService = WishListService;
        this.ResponseService = ResponseService;
        this.ProductService = ProductService;
    }

    /**
     * Get all wishlist items for specific user
     * @GetMapping ** = get request.
     * @Param user ** The authenticated user
     * @Errors Exception
     * @Returns http response
     */
    @GetMapping
    public ResponseEntity<WishList> getUsersOrders(@AuthenticationPrincipal AppUser user){
        try{
            List<WishList> items = WishListService.getItemsByUser(user);

            return ResponseService.SuccessResponse(items);
        } catch (Exception ex){
            return ResponseService.BadRequestResponse("Error getting wishlist items");
        }
    }

    /**
     * Add new item to wishlist
     * @PostMapping ** = post request.
     * @param user ** The authenticated user
     * @param itemDTO ** wishlist item to add
     * @Errors Exception
     * @Returns http response
     */
    @PostMapping("/create")
    public ResponseEntity markOrderAsSuccessful(@AuthenticationPrincipal AppUser user, @RequestBody WishListItemDTO itemDTO){
        try {
            /** Get product if exists */
            Product product = ProductService.getProductByID(itemDTO.getProductId());

            /** Check if user saved this product again */
            Boolean exists = WishListService.itemExists(user, product);
            if(exists){
                throw new Exception("item already exists in wishlist...");
            }

            /** Create wishlist item */
            WishList item = new WishList();

            /** connect item with authenticated user and product */
            item.setUser(user);
            item.setProduct(product);

            WishListService.addItemToWishlist(item);

            return ResponseService.SuccessResponse();

        } catch (IllegalArgumentException ex) {
            return ResponseService.BadRequestResponse("Wishlist item already exists");
        } catch(OptimisticLockingFailureException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        }catch(IllegalStateException ex){
            return ResponseService.BadRequestResponse("Selected product does not exist");
        } catch(Exception ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        }
    }

    /**
     * Delete item to wishlist
     * @DeleteMapping ** = delete request.
     * @param user ** The authenticated user
     * @param itemID ** product id
     * @Errors Exception
     * @return http response
     */
    @DeleteMapping("/{itemID}")
    public ResponseEntity RemoveItemFromWishlist(@AuthenticationPrincipal AppUser user, @PathVariable("itemID") int itemID){
        try{
            /** Get product if exists */
            Product product = ProductService.getProductByID(itemID);

            /** Check if products exists in wishlist */
            boolean exists = WishListService.itemExists(user, product);
            if(!exists){
                throw new RuntimeException("item does not exist in wishlist");
            }

            WishListService.removeItem(user, product);

            return ResponseService.SuccessResponseWithMsg("Item was successfully removed from wishlist");
        } catch (IllegalArgumentException ex){
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseService.BadRequestResponse(ex.getMessage());
        } catch (Exception ex){
            return ResponseService.BadRequestResponse("Error removing item from wishlist");
        }
    }
}
