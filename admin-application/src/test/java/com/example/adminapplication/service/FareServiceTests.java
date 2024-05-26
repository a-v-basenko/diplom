package com.example.adminapplication.service;

import com.example.adminapplication.model.Fare;
import com.example.adminapplication.repository.FareRepository;
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

public class FareServiceTests {

    @Mock
    private FareRepository fareRepository;

    @InjectMocks
    private FareService fareService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateFare() {
        Fare fare = new Fare();
        fare.setId(1);
        Mockito.when(fareRepository.save(Mockito.any(Fare.class))).thenReturn(fare);

        Fare createdFare = fareService.createFare(fare);

        Assertions.assertEquals(fare.getId(), createdFare.getId());
        Mockito.verify(fareRepository, Mockito.times(1)).save(fare);
    }

    @Test
    public void testGetFareById() {
        Fare fare = new Fare();
        fare.setId(1);
        Mockito.when(fareRepository.findById(1)).thenReturn(Optional.of(fare));

        Fare retrievedFare = fareService.getFareById(1);

        Assertions.assertNotNull(retrievedFare);
        Assertions.assertEquals(fare.getId(), retrievedFare.getId());
        Mockito.verify(fareRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetFareById_NotFound() {
        Mockito.when(fareRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Fare retrievedFare = fareService.getFareById(1);

        Assertions.assertNull(retrievedFare);
        Mockito.verify(fareRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetAllFares() {
        List<Fare> fareList = new ArrayList<>();
        fareList.add(new Fare());
        fareList.add(new Fare());

        Mockito.when(fareRepository.findAll(Mockito.any(Sort.class))).thenReturn(fareList);

        Iterable<Fare> retrievedFares = fareService.getAllFares();

        Assertions.assertEquals(fareList.size(), ((List<Fare>) retrievedFares).size());
        Mockito.verify(fareRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }

    @Test
    public void testUpdateFare() {
        Fare fare = new Fare();
        fare.setId(1);
        Mockito.when(fareRepository.save(Mockito.any(Fare.class))).thenReturn(fare);

        Fare updatedFare = fareService.updateFare(fare);

        Assertions.assertEquals(fare.getId(), updatedFare.getId());
        Mockito.verify(fareRepository, Mockito.times(1)).save(fare);
    }
}
