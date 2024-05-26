package com.example.adminapplication.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import javafx.beans.property.*;

@Entity
@Table(name = "top_up")
public class TopUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "machine", nullable = false)
    private Integer machineId;

    @Column(name = "card", nullable = false)
    private Integer cardId;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "amount", nullable = false, precision = 8, scale = 2)
    private BigDecimal amount;

    public TopUp() {
    }

    public TopUp(Integer machineId, Integer cardId, BigDecimal amount) {
        this.machineId = machineId;
        this.cardId = cardId;
        this.dateTime = LocalDateTime.now();
        this.amount = amount;
    }

    public TopUp(Integer id, Integer machineId, Integer cardId, LocalDateTime dateTime, BigDecimal amount) {
        this.id = id;
        this.machineId = machineId;
        this.cardId = cardId;
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public IntegerProperty getMachineProperty() {
        return new SimpleIntegerProperty(machineId);
    }

    public IntegerProperty getCardProperty() {
        return new SimpleIntegerProperty(cardId);
    }

    public StringProperty getDateTimeProperty() {
        return new SimpleStringProperty(dateTime.toString());
    }

    public DoubleProperty getAmountProperty() {
        return new SimpleDoubleProperty(amount.doubleValue());
    }
}
