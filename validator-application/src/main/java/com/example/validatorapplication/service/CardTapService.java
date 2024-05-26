package com.example.validatorapplication.service;

import com.example.validatorapplication.dto.CardTapDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class CardTapService {

    @Autowired
    private EntityManager entityManager;

    @Value("${validator.id}")
    private int validatorId;

    @Transactional
    public CardTapDTO insertCardTap(int cardId) {
        String sql = "SELECT fn_card_tap_insert(?, ?)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, cardId);
        query.setParameter(2, validatorId);
        // query.executeUpdate();
        int insertedId = (int) query.getSingleResult();

        sql = "SELECT * FROM card_tap WHERE id = ?";
        query = entityManager.createNativeQuery(sql);
        query.setParameter(1, insertedId);
        List<Object[]> resultList = query.getResultList();
        Object[] result = resultList.get(0);
        CardTapDTO inserted = new CardTapDTO(
                (Integer) result[0],
                (Integer) result[1],
                (Integer) result[2],
                (Timestamp) result[3],
                (BigDecimal) result[4],
                (BigDecimal) result[5],
                (Boolean) result[6],
                (String) result[7]
        );
        return inserted;
    }
}
