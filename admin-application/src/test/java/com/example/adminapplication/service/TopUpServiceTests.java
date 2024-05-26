package com.example.adminapplication.service;

import com.example.adminapplication.model.TopUp;
import com.example.adminapplication.repository.TopUpRepository;
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

public class TopUpServiceTests {

    @Mock
    private TopUpRepository topUpRepository;

    @InjectMocks
    private TopUpService topUpService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTopUpById() {
        TopUp topUp = new TopUp();
        topUp.setId(1);
        Mockito.when(topUpRepository.findById(1)).thenReturn(Optional.of(topUp));

        TopUp retrievedTopUp = topUpService.getTopUpById(1);

        Assertions.assertNotNull(retrievedTopUp);
        Assertions.assertEquals(topUp.getId(), retrievedTopUp.getId());
        Mockito.verify(topUpRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetTopUpById_NotFound() {
        Mockito.when(topUpRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        TopUp retrievedTopUp = topUpService.getTopUpById(1);

        Assertions.assertNull(retrievedTopUp);
        Mockito.verify(topUpRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetAllTopUps() {
        List<TopUp> topUpList = new ArrayList<>();
        topUpList.add(new TopUp());
        topUpList.add(new TopUp());

        Mockito.when(topUpRepository.findAll(Mockito.any(Sort.class))).thenReturn(topUpList);

        Iterable<TopUp> retrievedTopUps = topUpService.getAllTopUps();

        Assertions.assertEquals(topUpList.size(), ((List<TopUp>) retrievedTopUps).size());
        Mockito.verify(topUpRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }
}
