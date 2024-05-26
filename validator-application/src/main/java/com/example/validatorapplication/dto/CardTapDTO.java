package com.example.validatorapplication.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CardTapDTO {

    private Integer id;
    private Integer card;
    private Integer validator;
    private Timestamp dateTime;
    private BigDecimal fare;
    private BigDecimal withdrawAmount;
    private Boolean isSuccessful;
    private String note;

    public CardTapDTO() {

    }

    public CardTapDTO(Integer id, Integer card, Integer validator, Timestamp dateTime, BigDecimal fare, BigDecimal withdrawAmount, Boolean isSuccessful, String note) {
        this.id = id;
        this.card = card;
        this.validator = validator;
        this.dateTime = dateTime;
        this.fare = fare;
        this.withdrawAmount = withdrawAmount;
        this.isSuccessful = isSuccessful;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCard() {
        return card;
    }

    public void setCard(Integer card) {
        this.card = card;
    }

    public Integer getValidator() {
        return validator;
    }

    public void setValidator(Integer validator) {
        this.validator = validator;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public Boolean getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(Boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "CardTap{" + "id=" + id + ", card=" + card + ", validator=" + validator + ", dateTime=" + dateTime + ", fare=" + fare + ", withdrawAmount=" + withdrawAmount + ", isSuccessful=" + isSuccessful + ", note=" + note + '}';
    }
}
