package com.ct_ecommerce.eshop.WishList;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.Product.Product;
import jakarta.persistence.*;

/**
 * Class representing the wishlist items saved by the users
 * @Id ** primary key
 * @Entity ** database entity
 * @Table ** Table name in db
 */
@Entity
@Table(name = "wishlist")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    /** user of the wishlist item */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    /** Product */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /** Getters & Setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    /** Default Constructor */
    public WishList() {
    }

    /** Constructor */
    public WishList(AppUser user, Product product) {
        this.user = user;
        this.product = product;
    }
}
