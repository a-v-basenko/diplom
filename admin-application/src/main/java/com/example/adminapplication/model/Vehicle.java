package com.example.adminapplication.model;

import jakarta.persistence.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "license_plate", nullable = false, length = 10)
    private String licensePlate;

    public Vehicle() {
    }

    public Vehicle(Integer type, String licensePlate) {
        this.type = type;
        this.licensePlate = licensePlate;
    }

    public Vehicle(Integer id, Integer type, String licensePlate) {
        this.id = id;
        this.type = type;
        this.licensePlate = licensePlate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public IntegerProperty getIdProperty() {
        return new SimpleIntegerProperty(id);
    }

    public IntegerProperty getTypeProperty() {
        return new SimpleIntegerProperty(type);
    }

    public StringProperty getLicensePlateProperty() {
        return new SimpleStringProperty(licensePlate);
    }
}
