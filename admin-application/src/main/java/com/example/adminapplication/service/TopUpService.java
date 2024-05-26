package com.example.adminapplication.service;

import com.example.adminapplication.model.TopUp;
import com.example.adminapplication.repository.TopUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TopUpService {

    @Autowired
    private TopUpRepository topUpRepository;

    public TopUp getTopUpById(Integer id) {
        return topUpRepository.findById(id).orElse(null);
    }

    public Iterable<TopUp> getAllTopUps() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return topUpRepository.findAll(sort);
    }
}
