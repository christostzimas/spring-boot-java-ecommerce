package com.ct_ecommerce.eshop.Product.Fridge;

import com.ct_ecommerce.eshop.Product.Product;
import jakarta.persistence.*;


@Entity
@Table(name = "fridge_products")
public class Fridge extends Product {

    //
    // General info
    //
    // fields are nullable by default

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) int id;
    @Column(name = "lt_capacity", nullable = false)
    private int ltCapacity;
    @Column(name = "cooling_type", nullable = false)
    private String coolingType;
    @Column(name = "dimensions", nullable = false)
    private String dimensions;
    @Column(name = "annual_energy_consumption")
    private int annualEnergyConsumption;
    @Column(name = "energy_class", nullable = false)
    private char energyClass;
    @Column(name = "noise_energy_class")
    private char noiseEnergyClass;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "discounted_price")
    private double discountedPrice;


    public Fridge(String title, String description, double price, double discount, int stock, String brand, int ltCapacity, String coolingType, String dimensions, int annualEnergyConsumption, char energyClass, char noiseEnergyClass) {
        super(title, description, discount, stock, brand, "Fridge");
        this.price = price;
        this.ltCapacity = ltCapacity;
        this.coolingType = coolingType;
        this.dimensions = dimensions;
        this.annualEnergyConsumption = annualEnergyConsumption;
        this.energyClass = energyClass;
        this.noiseEnergyClass = noiseEnergyClass;
    }

    public Fridge(int price, int ltCapacity, String coolingType, String dimensions, int annualEnergyConsumption, char energyClass, char noiseEnergyClass) {
        this.price = price;
        this.ltCapacity = ltCapacity;
        this.coolingType = coolingType;
        this.dimensions = dimensions;
        this.annualEnergyConsumption = annualEnergyConsumption;
        this.energyClass = energyClass;
        this.noiseEnergyClass = noiseEnergyClass;
    }

    public Fridge(){}

    //@Override
    public int getId() {
        return id;
    }

    //@Override
    public void setId(int id) {
        this.id = id;
    }

    public int getLtCapacity() {
        return ltCapacity;
    }

    public void setLtCapacity(int ltCapacity) {
        this.ltCapacity = ltCapacity;
    }

    public String getCoolingType() {
        return coolingType;
    }

    public void setCoolingType(String coolingType) {
        this.coolingType = coolingType;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public int getAnnualEnergyConsumption() {
        return annualEnergyConsumption;
    }

    public void setAnnualEnergyConsumption(int annualEnergyConsumption) {
        this.annualEnergyConsumption = annualEnergyConsumption;
    }

    public char getEnergyClass() {
        return energyClass;
    }

    public void setEnergyClass(char energyClass) {
        this.energyClass = energyClass;
    }

    public char getNoiseEnergyClass() {
        return noiseEnergyClass;
    }

    public void setNoiseEnergyClass(char noiseEnergyClass) {
        this.noiseEnergyClass = noiseEnergyClass;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "id=" + id +
                ", ltCapacity=" + ltCapacity +
                ", coolingType='" + coolingType + '\'' +
                ", dimensions='" + dimensions + '\'' +
                ", annualEnergyConsumption=" + annualEnergyConsumption +
                ", energyClass=" + energyClass +
                ", noiseEnergyClass=" + noiseEnergyClass +
                '}';
    }
}
