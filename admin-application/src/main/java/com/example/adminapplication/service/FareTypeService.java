package com.example.adminapplication.service;

import com.example.adminapplication.model.FareType;
import com.example.adminapplication.repository.FareTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FareTypeService {

    @Autowired
    private FareTypeRepository fareTypeRepository;

    public FareType createFareType(FareType fareType) {
        return fareTypeRepository.save(fareType);
    }

    public FareType getFareTypeById(Integer id) {
        return fareTypeRepository.findById(id).orElse(null);
    }

    public Iterable<FareType> getAllFareTypes() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return fareTypeRepository.findAll(sort);
    }

    public FareType updateFareType(FareType fareType) {
        return fareTypeRepository.save(fareType);
    }
}
