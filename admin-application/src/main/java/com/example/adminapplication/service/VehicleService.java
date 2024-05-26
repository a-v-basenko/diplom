package com.example.adminapplication.service;

import com.example.adminapplication.model.Vehicle;
import com.example.adminapplication.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleById(Integer id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public Iterable<Vehicle> getAllVehicles() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return vehicleRepository.findAll(sort);
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
}
