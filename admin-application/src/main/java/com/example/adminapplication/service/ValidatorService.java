package com.example.adminapplication.service;

import com.example.adminapplication.model.Validator;
import com.example.adminapplication.repository.ValidatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

    @Autowired
    private ValidatorRepository validatorRepository;

    public Validator createValidator(Validator validator) {
        return validatorRepository.save(validator);
    }

    public Validator getValidatorById(Integer id) {
        return validatorRepository.findById(id).orElse(null);
    }

    public Iterable<Validator> getAllValidators() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return validatorRepository.findAll(sort);
    }
}
