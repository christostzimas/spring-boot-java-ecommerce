package com.ct_ecommerce.eshop.dto;

/**
 * Class that represents the product to be added in the wishlist
 */
public class WishListItemDTO {
    /** id of product */
    private int productId;

    /** Getter & setter */
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    /** Default Contractor */
    public WishListItemDTO() {
    }
}
