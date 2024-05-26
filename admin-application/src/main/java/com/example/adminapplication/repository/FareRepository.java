package com.example.adminapplication.repository;

import com.example.adminapplication.model.Fare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FareRepository extends JpaRepository<Fare, Integer> {

}
