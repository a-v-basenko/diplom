package com.example.adminapplication.service;

import com.example.adminapplication.model.FareType;
import com.example.adminapplication.repository.FareTypeRepository;
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

public class FareTypeServiceTests {

    @Mock
    private FareTypeRepository fareTypeRepository;

    @InjectMocks
    private FareTypeService fareTypeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateFareType() {
        FareType fareType = new FareType();
        fareType.setId(1);
        Mockito.when(fareTypeRepository.save(Mockito.any(FareType.class))).thenReturn(fareType);

        FareType createdFareType = fareTypeService.createFareType(fareType);

        Assertions.assertEquals(fareType.getId(), createdFareType.getId());
        Mockito.verify(fareTypeRepository, Mockito.times(1)).save(fareType);
    }

    @Test
    public void testGetFareTypeById() {
        FareType fareType = new FareType();
        fareType.setId(1);
        Mockito.when(fareTypeRepository.findById(1)).thenReturn(Optional.of(fareType));

        FareType retrievedFareType = fareTypeService.getFareTypeById(1);

        Assertions.assertNotNull(retrievedFareType);
        Assertions.assertEquals(fareType.getId(), retrievedFareType.getId());
        Mockito.verify(fareTypeRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetFareTypeById_NotFound() {
        Mockito.when(fareTypeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        FareType retrievedFareType = fareTypeService.getFareTypeById(1);

        Assertions.assertNull(retrievedFareType);
        Mockito.verify(fareTypeRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetAllFareTypes() {
        List<FareType> fareTypeList = new ArrayList<>();
        fareTypeList.add(new FareType());
        fareTypeList.add(new FareType());

        Mockito.when(fareTypeRepository.findAll(Mockito.any(Sort.class))).thenReturn(fareTypeList);

        Iterable<FareType> retrievedFareTypes = fareTypeService.getAllFareTypes();

        Assertions.assertEquals(fareTypeList.size(), ((List<FareType>) retrievedFareTypes).size());
        Mockito.verify(fareTypeRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }

    @Test
    public void testUpdateFareType() {
        FareType fareType = new FareType();
        fareType.setId(1);
        Mockito.when(fareTypeRepository.save(Mockito.any(FareType.class))).thenReturn(fareType);

        FareType updatedFareType = fareTypeService.updateFareType(fareType);

        Assertions.assertEquals(fareType.getId(), updatedFareType.getId());
        Mockito.verify(fareTypeRepository, Mockito.times(1)).save(fareType);
    }
}
