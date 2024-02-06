package com.ct_ecommerce.eshop.WishList;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {

    @Query("SELECT wishlistitem FROM WishList wishlistitem WHERE wishlistitem.user=:user")
    List<WishList> getAllByUser(@Param("user") AppUser user);

    @Query("SELECT wishlistitem FROM WishList wishlistitem WHERE wishlistitem.user=:user AND wishlistitem.product=:product")
    WishList itemExists(AppUser user, Product product);

    //DELETE FROM WishList WHERE user=:user AND product=:product
    void deleteByUserAndProduct(AppUser user, Product product);
}
