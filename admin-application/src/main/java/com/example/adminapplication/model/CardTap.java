package com.example.adminapplication.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import javafx.beans.property.*;

@Entity
@Table(name = "card_tap")
public class CardTap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "card", nullable = false)
    private Integer cardId;

    @Column(name = "validator", nullable = false)
    private Integer validatorId;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "fare", precision = 8, scale = 2)
    private BigDecimal fare;

    @Column(name = "withdraw_amount", precision = 8, scale = 2)
    private BigDecimal withdrawAmount;

    @Column(name = "is_successful", nullable = false)
    private Boolean successful;

    @Enumerated(EnumType.STRING)
    @Column(name = "note")
    private NoteType note;

    public CardTap() {
    }

    public CardTap(Integer cardId, Integer validatorId, LocalDateTime dateTime, BigDecimal fare, BigDecimal withdrawAmount, Boolean successful, NoteType note) {
        this.cardId = cardId;
        this.validatorId = validatorId;
        this.dateTime = dateTime;
        this.fare = fare;
        this.withdrawAmount = withdrawAmount;
        this.successful = successful;
        this.note = note;
    }

    public CardTap(Integer id, Integer cardId, Integer validatorId, LocalDateTime dateTime, BigDecimal fare, BigDecimal withdrawAmount, Boolean successful, NoteType note) {
        this.id = id;
        this.cardId = cardId;
        this.validatorId = validatorId;
        this.dateTime = dateTime;
        this.fare = fare;
        this.withdrawAmount = withdrawAmount;
        this.successful = successful;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getValidatorId() {
        return validatorId;
    }

    public void setValidatorId(Integer validatorId) {
        this.validatorId = validatorId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
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

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public NoteType getNote() {
        return note;
    }

    public void setNote(NoteType note) {
        this.note = note;
    }

    public IntegerProperty getIdProperty() {
        return new SimpleIntegerProperty(id);
    }

    public IntegerProperty getCardProperty() {
        return new SimpleIntegerProperty(cardId);
    }

    public IntegerProperty getValidatorProperty() {
        return new SimpleIntegerProperty(validatorId);
    }

    public StringProperty getDateTimeProperty() {
        return new SimpleStringProperty(dateTime.toString());
    }

    public DoubleProperty getFareProperty() {
        return new SimpleDoubleProperty(fare.doubleValue());
    }

    public DoubleProperty getWithdrawAmountProperty() {
        return new SimpleDoubleProperty(withdrawAmount.doubleValue());
    }

    public BooleanProperty getIsSuccessfulProperty() {
        return new SimpleBooleanProperty(successful);
    }

    public StringProperty getNoteProperty() {
        if (note == null) return new SimpleStringProperty("");
        return new SimpleStringProperty(note.toString());
    }
}
