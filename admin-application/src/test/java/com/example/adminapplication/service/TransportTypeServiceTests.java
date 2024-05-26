package com.example.adminapplication.service;

import com.example.adminapplication.model.TransportType;
import com.example.adminapplication.repository.TransportTypeRepository;
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

public class TransportTypeServiceTests {

    @Mock
    private TransportTypeRepository transportTypeRepository;

    @InjectMocks
    private TransportTypeService transportTypeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTransportType() {
        TransportType transportType = new TransportType();
        transportType.setId(1);
        Mockito.when(transportTypeRepository.save(Mockito.any(TransportType.class))).thenReturn(transportType);

        TransportType createdTransportType = transportTypeService.createTransportType(transportType);

        Assertions.assertEquals(transportType.getId(), createdTransportType.getId());
        Mockito.verify(transportTypeRepository, Mockito.times(1)).save(transportType);
    }

    @Test
    public void testGetTransportTypeById() {
        TransportType transportType = new TransportType();
        transportType.setId(1);
        Mockito.when(transportTypeRepository.findById(1)).thenReturn(Optional.of(transportType));

        TransportType retrievedTransportType = transportTypeService.getTransportTypeById(1);

        Assertions.assertNotNull(retrievedTransportType);
        Assertions.assertEquals(transportType.getId(), retrievedTransportType.getId());
        Mockito.verify(transportTypeRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetTransportTypeById_NotFound() {
        Mockito.when(transportTypeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        TransportType retrievedTransportType = transportTypeService.getTransportTypeById(1);

        Assertions.assertNull(retrievedTransportType);
        Mockito.verify(transportTypeRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetAllTransportTypes() {
        List<TransportType> transportTypeList = new ArrayList<>();
        transportTypeList.add(new TransportType());
        transportTypeList.add(new TransportType());

        Mockito.when(transportTypeRepository.findAll(Mockito.any(Sort.class))).thenReturn(transportTypeList);

        Iterable<TransportType> retrievedTransportTypes = transportTypeService.getAllTransportTypes();

        Assertions.assertEquals(transportTypeList.size(), ((List<TransportType>) retrievedTransportTypes).size());
        Mockito.verify(transportTypeRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }

    @Test
    public void testUpdateTransportType() {
        TransportType transportType = new TransportType();
        transportType.setId(1);
        Mockito.when(transportTypeRepository.save(Mockito.any(TransportType.class))).thenReturn(transportType);

        TransportType updatedTransportType = transportTypeService.updateTransportType(transportType);

        Assertions.assertEquals(transportType.getId(), updatedTransportType.getId());
        Mockito.verify(transportTypeRepository, Mockito.times(1)).save(transportType);
    }
}



