package com.ct_ecommerce.eshop;

import com.ct_ecommerce.eshop.Product.Product;
import com.ct_ecommerce.eshop.SuccessfulOrder.SuccessfulOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Class representing the quantities of items in a successful order
 * @Id ** primary key
 * @Entity ** database entity
 * @Table ** Table name in db
 */
@Entity
@Table(name = "successful_orders_quantities")
public class SuccessfulOrdersQuantities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    /** product being ordered. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    /** quantity of items. */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    /** Successful order containing item. */
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "successful_order_id", nullable = false)
    private SuccessfulOrder successfulOrder;

    /** Getters & Setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public SuccessfulOrder getOrder() {
        return successfulOrder;
    }

    public void setOrder(SuccessfulOrder order) {
        this.successfulOrder = order;
    }

    /** Default constructor */
    public SuccessfulOrdersQuantities() {
    }

    /** Constructor without id */
    public SuccessfulOrdersQuantities(Product product, Integer quantity, SuccessfulOrder order) {
        this.product = product;
        this.quantity = quantity;
        this.successfulOrder = order;
    }
}
