package com.example.adminapplication.service;

import com.example.adminapplication.model.TransportType;
import com.example.adminapplication.repository.TransportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TransportTypeService {

    @Autowired
    private TransportTypeRepository transportTypeRepository;

    public TransportType createTransportType(TransportType transportType) {
        return transportTypeRepository.save(transportType);
    }

    public TransportType getTransportTypeById(Integer id) {
        return transportTypeRepository.findById(id).orElse(null);
    }

    public Iterable<TransportType> getAllTransportTypes() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return transportTypeRepository.findAll(sort);
    }

    public TransportType updateTransportType(TransportType transportType) {
        return transportTypeRepository.save(transportType);
    }
}
