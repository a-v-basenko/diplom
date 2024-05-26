package com.example.adminapplication.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

@Entity
@Table(name = "fare")
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", nullable = false)
    private Integer typeId;

    @Column(name = "transport_type", nullable = false)
    private Integer transportTypeId;

    @Column(name = "fare", nullable = false, precision = 8, scale = 2)
    private BigDecimal amount;

    public Fare() {
    }

    public Fare(Integer typeId, Integer transportTypeId, BigDecimal amount) {
        this.typeId = typeId;
        this.transportTypeId = transportTypeId;
        this.amount = amount;
    }

    public Fare(Integer id, Integer typeId, Integer transportTypeId, BigDecimal amount) {
        this.id = id;
        this.typeId = typeId;
        this.transportTypeId = transportTypeId;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getTransportTypeId() {
        return transportTypeId;
    }

    public void setTransportTypeId(Integer transportTypeId) {
        this.transportTypeId = transportTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public IntegerProperty getIdProperty() {
        return new SimpleIntegerProperty(id);
    }

    public IntegerProperty getTypeProperty() {
        return new SimpleIntegerProperty(typeId);
    }

    public IntegerProperty getTransportTypeProperty() {
        return new SimpleIntegerProperty(transportTypeId);
    }

    public DoubleProperty getFareProperty() {
        return new SimpleDoubleProperty(amount.doubleValue());
    }
}
