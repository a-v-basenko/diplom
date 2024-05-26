package com.example.adminapplication.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", nullable = false)
    private Integer typeId;

    @Column(name = "balance", nullable = false, precision = 8, scale = 2)
    private BigDecimal balance;

    public Card() {
    }

    public Card(Integer typeId, BigDecimal balance) {
        this.typeId = typeId;
        this.balance = balance;
    }

    public Card(Integer id, Integer typeId, BigDecimal balance) {
        this.id = id;
        this.typeId = typeId;
        this.balance = balance;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public IntegerProperty getIdProperty() {
        return new SimpleIntegerProperty(id);
    }

    public IntegerProperty getTypeProperty() {
        return new SimpleIntegerProperty(typeId);
    }

    public DoubleProperty getBalanceProperty() {
        return new SimpleDoubleProperty(balance.doubleValue());
    }
}
