package com.ct_ecommerce.eshop.dto;

/**
 * Class that represents products that can't fulfill the order due to low stock.
 */
public class UnavailableProductsDTO {
    /** Id of product */
    private int productId;
    /** Name of product */
    private String productName;
    /** Current stock of product */
    private int currentStock;

    /** Default constructor */
    public UnavailableProductsDTO() {
    }

    /** Full constructor */
    public UnavailableProductsDTO(int productId, String productName, int currentStock) {
        this.productId = productId;
        this.productName = productName;
        this.currentStock = currentStock;
    }

    /** Getters and setters */
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    /** toString */
    @Override
    public String toString() {
        return "Product " + productName +
                ", has current stock: " + currentStock +
                " so the order can be partial fulfilled ";
    }
}
