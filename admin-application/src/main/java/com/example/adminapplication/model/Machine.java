package com.example.adminapplication.model;

import jakarta.persistence.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "machine")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    public Machine() {
    }

    public Machine(String address) {
        this.address = address;
    }

    public Machine(Integer id, String address) {
        this.id = id;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public IntegerProperty getIdProperty() {
        return new SimpleIntegerProperty(id);
    }

    public StringProperty getAddressProperty() {
        return new SimpleStringProperty(address);
    }
}
