package com.example.adminapplication.repository;

import com.example.adminapplication.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {

}
