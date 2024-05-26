package com.example.adminapplication.service;

import com.example.adminapplication.model.Fare;
import com.example.adminapplication.repository.FareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FareService {

    @Autowired
    private FareRepository fareRepository;

    public Fare createFare(Fare fare) {
        return fareRepository.save(fare);
    }

    public Fare getFareById(Integer id) {
        return fareRepository.findById(id).orElse(null);
    }

    public Iterable<Fare> getAllFares() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return fareRepository.findAll(sort);
    }

    public Fare updateFare(Fare fare) {
        return fareRepository.save(fare);
    }
}
