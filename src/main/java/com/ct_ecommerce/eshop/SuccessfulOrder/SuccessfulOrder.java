package com.ct_ecommerce.eshop.SuccessfulOrder;

import com.ct_ecommerce.eshop.AppUsers.Address.Address;
import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.SuccessfulOrdersQuantities;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the successful orders
 * @Id ** primary key
 * @Entity ** database entity
 * @Table ** Table name in db
 */
@Entity
@Table(name = "successful_orders")
public class SuccessfulOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    /** user of the order */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;
    /** address of the order */
    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
    /** quantities ordered. */
    @OneToMany(mappedBy = "successfulOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SuccessfulOrdersQuantities> quantities = new ArrayList<>();
    @Column(name="total_price")
    private  Double totalOrderPrice;
    @Column(name = "order_number")
    private Long orderNumber;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    /** getters & Setters */
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<SuccessfulOrdersQuantities> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<SuccessfulOrdersQuantities> quantities) {
        this.quantities = quantities;
    }

    public Double getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(Double totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /** Default constructor */
    public SuccessfulOrder() {
    }

    /** Constructor without id */
    public SuccessfulOrder(AppUser user, Address address, List<SuccessfulOrdersQuantities> quantities, Double totalOrderPrice, Long orderNumber) {
        this.user = user;
        this.address = address;
        this.quantities = quantities;
        this.totalOrderPrice = totalOrderPrice;
        this.orderNumber = orderNumber;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
