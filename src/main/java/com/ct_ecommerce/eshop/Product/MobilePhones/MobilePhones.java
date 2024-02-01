package com.ct_ecommerce.eshop.Product.MobilePhones;

import com.ct_ecommerce.eshop.Product.Product;
import jakarta.persistence.*;

/**
 * Class representing MobilePhones that extent Product class
 * @Id ** primary key
 * @Entity ** database entity
 * @Table ** Table name in db
 */
@Entity
@Table(name = "mobile_phones")
public class MobilePhones extends Product {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) int id;
    @Column(name = "operating_system", nullable = false)
    private String operatingSystem;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "box_contents")
    private String boxContents;
    @Column(name = "processor", nullable = false)
    private String processor;
    @Column(name = "processor_speed")
    private double processorSpeed;
    @Column(name = "ram_size")
    private int ramSize;
    @Column(name = "screen_size", nullable = false)
    private double screenSize;
    @Column(name = "screen_resolution", nullable = false)
    private String screenResolution;
    @Column(name = "screen_refresh_rate", nullable = false)
    private int screenRefreshRate;
    @Column(name = "battery_size", nullable = false)
    private int batterySize;

    /** constructor all fields with super class fields */
    public MobilePhones(String titleProduct, String descriptionProduct, double discountPrice, double price, int stockProduct, String brandProduct, String categoryProduct, int id, String operatingSystem, String model, String boxContents, String processor, double processorSpeed, int ramSize, double screenSize, String screenResolution, int screenRefreshRate, int batterySize) {
        super(titleProduct, descriptionProduct, discountPrice, price, stockProduct, brandProduct, categoryProduct);
        this.operatingSystem = operatingSystem;
        this.model = model;
        this.boxContents = boxContents;
        this.processor = processor;
        this.processorSpeed = processorSpeed;
        this.ramSize = ramSize;
        this.screenSize = screenSize;
        this.screenResolution = screenResolution;
        this.screenRefreshRate = screenRefreshRate;
        this.batterySize = batterySize;
    }

    /** constructor with all fields of current class */
    public MobilePhones(String operatingSystem, String model, String boxContents, String processor, double processorSpeed, int ramSize, double screenSize, String screenResolution, int screenRefreshRate, int batterySize) {
        this.operatingSystem = operatingSystem;
        this.model = model;
        this.boxContents = boxContents;
        this.processor = processor;
        this.processorSpeed = processorSpeed;
        this.ramSize = ramSize;
        this.screenSize = screenSize;
        this.screenResolution = screenResolution;
        this.screenRefreshRate = screenRefreshRate;
        this.batterySize = batterySize;
    }

    /** Empty constructor */
    public MobilePhones() {}

    /** Getters & setters */
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBoxContents() {
        return boxContents;
    }

    public void setBoxContents(String boxContents) {
        this.boxContents = boxContents;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public double getProcessorSpeed() {
        return processorSpeed;
    }

    public void setProcessorSpeed(double processorSpeed) {
        this.processorSpeed = processorSpeed;
    }

    public int getRamSize() {
        return ramSize;
    }

    public void setRamSize(int ramSize) {
        this.ramSize = ramSize;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    public int getScreenRefreshRate() {
        return screenRefreshRate;
    }

    public void setScreenRefreshRate(int screenRefreshRate) {
        this.screenRefreshRate = screenRefreshRate;
    }

    public int getBatterySize() {
        return batterySize;
    }

    public void setBatterySize(int batterySize) {
        this.batterySize = batterySize;
    }

    /** toString */
    @Override
    public String toString() {
        return "MobilePhones{" +
                "id=" + id +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", model='" + model + '\'' +
                ", boxContents='" + boxContents + '\'' +
                ", processor='" + processor + '\'' +
                ", processorSpeed=" + processorSpeed +
                ", ramSize=" + ramSize +
                ", screenSize=" + screenSize +
                ", screenResolution='" + screenResolution + '\'' +
                ", screenRefreshRate=" + screenRefreshRate +
                ", batterySize=" + batterySize +
                '}';
    }

}
