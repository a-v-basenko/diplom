package com.example.adminapplication.model;

import jakarta.persistence.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

@Entity
@Table(name = "validator")
public class Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vehicle", nullable = false)
    private Integer vehicleId;

    public Validator() {
    }

    public Validator(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Validator(Integer id, Integer vehicleId) {
        this.id = id;
        this.vehicleId = vehicleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public IntegerProperty getIdProperty() {
        return new SimpleIntegerProperty(id);
    }

    public IntegerProperty getVehicleIdProperty() {
        return new SimpleIntegerProperty(vehicleId);
    }
}
