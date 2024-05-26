package com.example.adminapplication.service;

import com.example.adminapplication.model.Machine;
import com.example.adminapplication.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MachineService {

    @Autowired
    private MachineRepository machineRepository;

    public Machine createMachine(Machine machine) {
        return machineRepository.save(machine);
    }

    public Machine getMachineById(Integer id) {
        return machineRepository.findById(id).orElse(null);
    }

    public Iterable<Machine> getAllMachines() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return machineRepository.findAll(sort);
    }

    public Machine updateMachine(Machine machine) {
        return machineRepository.save(machine);
    }
}
