package com.ct_ecommerce.eshop.Product;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Products")
public class Product {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) int id;
    private String title;
    private String description;
    private double price;
    private double discount;
    private int stock;
    private int sales;
    private String brand;
    private String category;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Product(int idProduct, String titleProduct, String descriptionProduct, double priceProduct, double discountPrice, int stockProduct, String brandProduct, String categoryProduct) {
        this.id = idProduct;
        this.title = titleProduct;
        this.description = descriptionProduct;
        this.price = priceProduct;
        this.discount = discountPrice;
        this.stock = stockProduct;
        this.sales = 0;
        this.brand = brandProduct;
        this.category = categoryProduct;
        this.updated_at = LocalDateTime.now();
        this.created_at = LocalDateTime.now();
    }

    public Product(String titleProduct, String descriptionProduct, double priceProduct, double discountPrice, int stockProduct, String brandProduct, String categoryProduct) {
        this.title = titleProduct;
        this.description = descriptionProduct;
        this.price = priceProduct;
        this.discount = discountPrice;
        this.stock = stockProduct;
        this.sales = 0;
        this.brand = brandProduct;
        this.category = categoryProduct;
        this.updated_at = LocalDateTime.now();
        this.created_at = LocalDateTime.now();
    }

    public Product() {
    }

    //calculate discounts
    public double discountPercentage(){
        if(this.discount == 0){
            return this.price;
        }
        return this.price - (this.price - this.discount/100);
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    // toString ??????
}
