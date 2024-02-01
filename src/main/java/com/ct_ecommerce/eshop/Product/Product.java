package com.ct_ecommerce.eshop.Product;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class representing products - super class of fridge and MobilePhone
 * @Id ** primary key
 * @Entity ** database entity
 * @Table ** Table name in db
 */
@Entity
@Table(name = "Products")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) int id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    private double discount;
    @Column(name = "stock", nullable = false)
    private int stock;
    private int sales;
    @Column(name = "brand", nullable = false)
    private String brand;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "price", nullable = false)
    private double price;
    /**
     * finalDiscountedPrice is used to send the discounted price
     * along with the original to be used in the front end app
     * @Transient ** annotation that prevents this variable from being a column in db
     */
    @Transient
    private double finalDiscountedPrice;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    /** constructor all fields */
    public Product(int idProduct, String titleProduct, String descriptionProduct, double discountPrice, double price, int stockProduct, String brandProduct, String categoryProduct) {
        this.id = idProduct;
        this.title = titleProduct;
        this.description = descriptionProduct;
        this.discount = discountPrice;
        this.price = price;
        this.stock = stockProduct;
        this.sales = 0;
        this.brand = brandProduct;
        this.category = categoryProduct;
        this.updated_at = LocalDateTime.now();
        this.created_at = LocalDateTime.now();
    }

    /** constructor without id */
    public Product(String titleProduct, String descriptionProduct, double discountPrice, double price, int stockProduct, String brandProduct, String categoryProduct) {
        this.title = titleProduct;
        this.description = descriptionProduct;
        this.discount = discountPrice;
        this.price = price;
        this.stock = stockProduct;
        this.sales = 0;
        this.brand = brandProduct;
        this.category = categoryProduct;
        this.updated_at = LocalDateTime.now();
        this.created_at = LocalDateTime.now();
    }

    /** Empty constructor */
    public Product() {
    }

    /**
     * Calculates discounted price for one product
     * uses discount, price
     * @return discounted price
     */
    public double discountPercentage(){
        if(this.discount == 0){
            return this.price;
        }
        return this.price - (this.price * (this.discount/100));
    }

    /**
     * Updates list containing Products objects or any subclass
     * @return the updated list including discounted price
     */
    //set discounted prices
    public List<? extends Product> calculateDiscounts(List<? extends Product> products){
        for (Product product : products){
            if(product.discount > 0){
                product.setFinalDiscountedPrice(product.discountPercentage());
            }
        }

        return products;
    }

    /** Getters & setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFinalDiscountedPrice() {
        return finalDiscountedPrice;
    }

    public void setFinalDiscountedPrice(double finalDiscountedPrice) {
        this.finalDiscountedPrice = finalDiscountedPrice;
    }

    /** toString */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", discount=" + discount +
                ", stock=" + stock +
                ", sales=" + sales +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
