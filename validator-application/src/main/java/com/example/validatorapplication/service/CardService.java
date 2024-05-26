package com.example.validatorapplication.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public boolean cardExists(int cardId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM card WHERE id = ?)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, cardId);
        return (boolean) query.getSingleResult();
    }

    @Transactional
    public BigDecimal getCardBalance(int cardId) {
        String sql = "SELECT balance FROM card WHERE id = ?";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, cardId);
        return (BigDecimal) query.getSingleResult();
    }

}
