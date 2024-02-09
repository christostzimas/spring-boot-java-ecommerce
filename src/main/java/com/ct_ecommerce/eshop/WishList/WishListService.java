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
        List<WishList> items = WishListRepository.getAllByUser(user);

        return items;
    }

    /**
     * Add wishlist items for specific user
     * @param item The wishlist item
     */
    public void addItemToWishlist(WishList item) {
        WishListRepository.save(item);
    }

    /**
     * Check if user has already saved this item
     * @param product The product
     * @param user The user who owns the item
     */
    public boolean itemExists(AppUser user, Product product){
        WishList item = WishListRepository.itemExists(user, product);

        if(item == null){
            return false;
        }

        return true;
    }

    /**
     * Remove item from wishlist
     * @param user The user that item belongs to
     * @param product The product to remove
     */
    @Transactional
    public void removeItem(AppUser user, Product product) {
        WishListRepository.deleteByUserAndProduct(user, product);
    }
}
