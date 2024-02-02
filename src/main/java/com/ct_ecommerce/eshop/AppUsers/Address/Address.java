package com.ct_ecommerce.eshop.AppUsers.Address;

import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "post_code", nullable = false, length = 75)
    private String postCode;
    @Column(name = "active")
    private Boolean activeAddress;
    @JsonIgnore
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Boolean getActiveAddress() {
        return activeAddress;
    }

    public void setActiveAddress(Boolean activeAddress) {
        this.activeAddress = activeAddress;
    }

    /** Default constructor */
    public Address(){}

    /** full constructor */
    public Address(int id, String addressLine1, String addressLine2, String city, String country, String postCode, AppUser user, Boolean active) {
        this.id = id;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
        this.activeAddress = active;
        this.user = user;
    }

    /** constructor without id */
    public Address(String addressLine1, String addressLine2, String city, String country, String postCode, AppUser user, Boolean active) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
        this.activeAddress = active;
        this.user = user;
    }
}
