package com.example.adminapplication.service;

import com.example.adminapplication.model.Machine;
import com.example.adminapplication.repository.MachineRepository;
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

public class MachineServiceTests {

    @Mock
    private MachineRepository machineRepository;

    @InjectMocks
    private MachineService machineService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateMachine() {
        Machine machine = new Machine();
        machine.setId(1);
        Mockito.when(machineRepository.save(Mockito.any(Machine.class))).thenReturn(machine);

        Machine createdMachine = machineService.createMachine(machine);

        Assertions.assertEquals(machine.getId(), createdMachine.getId());
        Mockito.verify(machineRepository, Mockito.times(1)).save(machine);
    }

    @Test
    public void testGetMachineById() {
        Machine machine = new Machine();
        machine.setId(1);
        Mockito.when(machineRepository.findById(1)).thenReturn(Optional.of(machine));

        Machine retrievedMachine = machineService.getMachineById(1);

        Assertions.assertNotNull(retrievedMachine);
        Assertions.assertEquals(machine.getId(), retrievedMachine.getId());
        Mockito.verify(machineRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetMachineById_NotFound() {
        Mockito.when(machineRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Machine retrievedMachine = machineService.getMachineById(1);

        Assertions.assertNull(retrievedMachine);
        Mockito.verify(machineRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void testGetAllMachines() {
        List<Machine> machineList = new ArrayList<>();
        machineList.add(new Machine());
        machineList.add(new Machine());

        Mockito.when(machineRepository.findAll(Mockito.any(Sort.class))).thenReturn(machineList);

        Iterable<Machine> retrievedMachines = machineService.getAllMachines();

        Assertions.assertEquals(machineList.size(), ((List<Machine>) retrievedMachines).size());
        Mockito.verify(machineRepository, Mockito.times(1)).findAll(Mockito.any(Sort.class));
    }

    @Test
    public void testUpdateMachine() {
        Machine machine = new Machine();
        machine.setId(1);
        Mockito.when(machineRepository.save(Mockito.any(Machine.class))).thenReturn(machine);

        Machine updatedMachine = machineService.updateMachine(machine);

        Assertions.assertEquals(machine.getId(), updatedMachine.getId());
        Mockito.verify(machineRepository, Mockito.times(1)).save(machine);
    }
}

