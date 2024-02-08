package com.ct_ecommerce.eshop.dto;

public class OrderReviewDTO {

    private double startRating;
    private String comment;
    private int productId;

    /** Getters & Setters */
    public double getStartRating() {
        return startRating;
    }

    public void setStartRating(double startRating) {
        this.startRating = startRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
