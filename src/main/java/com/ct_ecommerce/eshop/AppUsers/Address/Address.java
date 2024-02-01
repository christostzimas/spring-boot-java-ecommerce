package com.ct_ecommerce.eshop.AppUsers.Address;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import jakarta.persistence.*;

/**
 * Class for user address
 * @Id ** primary key
 */

@Entity
@Table(name = "user_addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "address_line_1", nullable = false, length = 512)
    private String addressLine1;
    @Column(name = "address_line_2", length = 512)
    private String addressLine2;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "country", nullable = false, length = 75) //longest country name 56 chars
    private String country;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
