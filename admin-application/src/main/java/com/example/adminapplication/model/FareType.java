package com.example.adminapplication.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import javafx.beans.property.*;

@Entity
@Table(name = "fare_type")
public class FareType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "daily_limit", nullable = false, precision = 8, scale = 2)
    private BigDecimal dailyLimit;

    @Column(name = "weekly_limit", nullable = false, precision = 8, scale = 2)
    private BigDecimal weeklyLimit;


    public FareType() {
    }

    public FareType(String name, BigDecimal dailyLimit, BigDecimal weeklyLimit) {
        this.name = name;
        this.dailyLimit = dailyLimit;
        this.weeklyLimit = weeklyLimit;
    }

    public FareType(Integer id, String name, BigDecimal dailyLimit, BigDecimal weeklyLimit) {
        this.id = id;
        this.name = name;
        this.dailyLimit = dailyLimit;
        this.weeklyLimit = weeklyLimit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public BigDecimal getWeeklyLimit() {
        return weeklyLimit;
    }

    public void setWeeklyLimit(BigDecimal weeklyLimit) {
        this.weeklyLimit = weeklyLimit;
    }

    public IntegerProperty getIdProperty() {
        return new SimpleIntegerProperty(id);
    }

    public StringProperty getNameProperty() {
        return new SimpleStringProperty(name);
    }

    public DoubleProperty getDailyLimitProperty() {
        return new SimpleDoubleProperty(dailyLimit.doubleValue());
    }

    public DoubleProperty getWeeklyLimitProperty() {
        return new SimpleDoubleProperty(weeklyLimit.doubleValue());
    }
}
