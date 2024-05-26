package com.example.adminapplication.service;

import com.example.adminapplication.model.Validator;
import com.example.adminapplication.repository.ValidatorRepository;
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

public class ValidatorServiceTests {

    @Mock
    private ValidatorRepository validatorRepository;

    @InjectMocks
    private ValidatorService validatorService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateValidator() {
        Validator validator = new Validator();
        validator.setId(1);
        Mockito.when(validatorRepository.save(Mockito.any(Validator.class))).thenReturn(validator);

        Validator createdValidator = validatorService.createValidator(validator);

        Assertions.assertEquals(validator.getId(), createdValidator.getId());
        Mockito.verify(validatorRepository, Mockito.times(1)).save(validator);
    }

    @Test
    public void testGetValidatorById() {
        Validator validator = new Validator();
        validator.setId(1);
        Mockito.when(validatorRepository.findById(1)).thenReturn(Optional.of(validator));

        Validator retrievedValidator = validatorService.getValidatorById(1);

        Assertions.assertNotNull(retrievedValidator);
        Assertions.assertEquals(validator.getId(), retrievedValidator.getId());
        Mockito.verify(validatorRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetValidatorById_NotFound() {
        Mockito.when(validatorRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Validator retrievedValidator = validatorService.getValidatorById(1);

        Assertions.assertNull(retrievedValidator);
        Mockito.verify(validatorRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetAllValidators() {
        List<Validator> validatorList = new ArrayList<>();
        validatorList.add(new Validator());
        validatorList.add(new Validator());

        Mockito.when(validatorRepository.findAll(Mockito.any(Sort.class))).thenReturn(validatorList);

        Iterable<Validator> retrievedValidators = validatorService.getAllValidators();

        Assertions.assertEquals(validatorList.size(), ((List<Validator>) retrievedValidators).size());
        Mockito.verify(validatorRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }
}





