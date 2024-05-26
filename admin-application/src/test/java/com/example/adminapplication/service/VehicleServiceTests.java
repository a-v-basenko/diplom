package com.example.adminapplication.service;

import com.example.adminapplication.model.Vehicle;
import com.example.adminapplication.repository.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleServiceTests {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        Mockito.when(vehicleRepository.save(Mockito.any(Vehicle.class))).thenReturn(vehicle);

        Vehicle createdVehicle = vehicleService.createVehicle(vehicle);

        Assertions.assertEquals(vehicle.getId(), createdVehicle.getId());
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(vehicle);
    }

    @Test
    public void testGetVehicleById() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        Mockito.when(vehicleRepository.findById(1)).thenReturn(Optional.of(vehicle));

        Vehicle retrievedVehicle = vehicleService.getVehicleById(1);

        Assertions.assertNotNull(retrievedVehicle);
        Assertions.assertEquals(vehicle.getId(), retrievedVehicle.getId());
        Mockito.verify(vehicleRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetVehicleById_NotFound() {
        Mockito.when(vehicleRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Vehicle retrievedVehicle = vehicleService.getVehicleById(1);

        Assertions.assertNull(retrievedVehicle);
        Mockito.verify(vehicleRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetAllVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(new Vehicle());
        vehicleList.add(new Vehicle());

        Mockito.when(vehicleRepository.findAll(Mockito.any(Sort.class))).thenReturn(vehicleList);

        Iterable<Vehicle> retrievedVehicles = vehicleService.getAllVehicles();

        Assertions.assertEquals(vehicleList.size(), ((List<Vehicle>) retrievedVehicles).size());
        Mockito.verify(vehicleRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }

    @Test
    public void testUpdateVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        Mockito.when(vehicleRepository.save(Mockito.any(Vehicle.class))).thenReturn(vehicle);

        Vehicle updatedVehicle = vehicleService.updateVehicle(vehicle);

        Assertions.assertEquals(vehicle.getId(), updatedVehicle.getId());
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(vehicle);
    }
}
