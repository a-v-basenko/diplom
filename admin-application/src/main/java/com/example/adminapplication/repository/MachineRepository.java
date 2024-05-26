package com.example.adminapplication.repository;

import com.example.adminapplication.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Integer> {

}
