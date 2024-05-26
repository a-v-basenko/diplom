package com.example.machineapplication.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TopUpService {

    @Autowired
    private EntityManager entityManager;

    @Value("${machine.id}")
    private int machineId;

    @Transactional
    public void insertTopUp(int cardId, int amount) {
        String sql = "INSERT INTO top_up (machine, card, amount) VALUES (?, ?, ?)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, machineId);
        query.setParameter(2, cardId);
        query.setParameter(3, amount);
        query.executeUpdate();
    }
}
