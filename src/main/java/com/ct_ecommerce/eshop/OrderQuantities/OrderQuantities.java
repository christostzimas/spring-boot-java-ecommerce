package com.ct_ecommerce.eshop.OrderQuantities;

import com.ct_ecommerce.eshop.Order.Order;
import com.ct_ecommerce.eshop.Product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Class representing the quantities of items in an order
 * @Id ** primary key
 * @Entity ** database entity
 * @Table ** Table name in db
 */

@Entity
@Table(name = "order_quantities")
public class OrderQuantities {
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
    /** Order containing item. */
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
