package com.ct_ecommerce.eshop.dto;

/**
 * Class that represents the order number of a pending order
 * Used to mark an order as successful unique the unique order number
 */
public class OrderNumberDTO {
    private long orderNumber;

    /** Getters Setters */
    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    /** Default constructor */
    public OrderNumberDTO() {
    }

    /** Constructor */
    public OrderNumberDTO(long orderNumber) {
        this.orderNumber = orderNumber;
    }
}
