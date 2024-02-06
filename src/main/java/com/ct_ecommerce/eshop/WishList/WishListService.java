package com.ct_ecommerce.eshop.WishList;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.Product.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer wishlist
 * @Variable SuccessfulOrderRepository ** repository layer for wishlist items.
 */
@Service
public class WishListService {
    private final WishListRepository WishListRepository;

    /**
     * Constructor
     * @param WishListRepository ** used for db actions
     */
    public WishListService(WishListRepository WishListRepository) {
        this.WishListRepository = WishListRepository;
    }

    /**
     * Get all wishlist items for specific user
     * @param user The authenticated user.
     * @return The List of WishList items.
     */
    public List<WishList> getItemsByUser(AppUser user) {
        try {
            List<WishList> items = WishListRepository.getAllByUser(user);

            return items;
        } catch(Exception ex){
            throw new RuntimeException("Error getting orders of user", ex);
        }
    }

    /**
     * Add wishlist items for specific user
     * @param item The wishlist item
     */
    public void addItemToWishlist(WishList item) {
        try {
            WishListRepository.save(item);
        } catch(Exception ex){
            throw new RuntimeException("Error getting orders of user", ex);
        }
    }

    /**
     * Check if user has already saved this item
     * @param product The product
     * @param user The user who owns the item
     */
    public boolean itemExists(AppUser user, Product product){
        try{
            WishList item = WishListRepository.itemExists(user, product);

            if(item == null){
                return false;
            }
            return true;
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            throw new RuntimeException("Error checking if product exists.." + ex);
        }
    }

    /**
     * Remove item from wishlist
     * @param user The user that item belongs to
     * @param product The product to remove
     */
    @Transactional
    public void removeItem(AppUser user, Product product) {
        try{

            WishListRepository.deleteByUserAndProduct(user, product);

        } catch (Exception ex){
            throw new RuntimeException("Error deleting product from wishlist.." + ex);
        }
    }
}
