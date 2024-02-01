package com.ct_ecommerce.eshop.ProductOrders;

import com.ct_ecommerce.eshop.AppUsers.Address.Address;
import com.ct_ecommerce.eshop.AppUsers.AppUser;
import jakarta.persistence.*;

/**
 * Class representing orders
 * @Id ** primary key
 * @Entity ** database entity
 * @Table ** Table name in db
 */
@Entity
@Table(name = "product_orders")
public class ProductOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
}
