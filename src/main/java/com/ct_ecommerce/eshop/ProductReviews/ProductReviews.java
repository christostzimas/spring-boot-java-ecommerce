package com.ct_ecommerce.eshop.ProductReviews;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.Product.Product;
import jakarta.persistence.*;

/**
 * Class representing product reviews of the app
 * @Id ** primary key
 * @Entity ** database entity
 * @Table ** Table name in db
 * @JsonIgnore ** excludes fields with this annotation from responses
 */
@Entity
@Table(name = "product_reviews")
public class ProductReviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "stars", nullable = false)
    private double startsRating;

    @Column(name = "comment", length = 1550)
    private String comment;

    /** product of review */
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product_id;

    /** user of review */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    /** Getters & setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getStartsRating() {
        return startsRating;
    }

    public void setStartsRating(double startsRating) {
        this.startsRating = startsRating;
    }

    public Product getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Product product_id) {
        this.product_id = product_id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /** Default Constructor */
    public ProductReviews() {
    }

    /** Constructor */
    public ProductReviews(double startsRating, Product product_id, AppUser user, String comment) {
        this.startsRating = startsRating;
        this.product_id = product_id;
        this.user = user;
        this.comment = comment;
    }
}
