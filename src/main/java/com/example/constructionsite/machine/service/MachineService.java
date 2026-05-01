package com.example.constructionsite.machine.service;

import com.example.constructionsite.machine.dto.request.MachineRequest;
import com.example.constructionsite.machine.dto.response.MachineResponse;
import com.example.constructionsite.machine.entity.MachineEntity;
import com.example.constructionsite.machine.repository.MachineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MachineService {

  private final MachineRepository machineRepository;

  public Page<MachineResponse> findAllMachines(Pageable pageable) {
    return machineRepository.findAll(pageable)
        .map(this::buildMachineResponse);
  }

  public MachineResponse findMachineById(Long id) {
    MachineEntity machineEntity = machineRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Masina nije pronadjena, id: " + id));
    return buildMachineResponse(machineEntity);
  }

  private MachineResponse buildMachineResponse(MachineEntity machineEntity) {
    return MachineResponse.builder()
        .id(machineEntity.getId())
        .name(machineEntity.getName())
        .build();
  }

  public MachineResponse createMachine(MachineRequest machineRequest) {
    ensureMachineNameIsUnique(machineRequest.getName());
    MachineEntity machineEntity = buildMachineEntity(machineRequest);
    MachineEntity savedMachineEntity = machineRepository.save(machineEntity);
    return buildMachineResponse(savedMachineEntity);
  }

  private void ensureMachineNameIsUnique(String name) {
    if (machineRepository.existsByName(name)) {
      throw new RuntimeException("Mašina sa imenom '" + name + "' već postoji");
    }
  }

  private MachineEntity buildMachineEntity(MachineRequest request) {
    MachineEntity machineEntity = new MachineEntity();
    machineEntity.setName(request.getName());
    return machineEntity;
  }

  public MachineResponse updateMachine(Long id, MachineRequest machineRequest) {
    MachineEntity existingMachineEntity = findMachineByIdOrThrow(id);
    ensureMachineNameIsUniqueForUpdate(existingMachineEntity, machineRequest.getName());
    existingMachineEntity.setName(machineRequest.getName());
    MachineEntity savedMachineEntity = machineRepository.save(existingMachineEntity);
    return buildMachineResponse(savedMachineEntity);
  }

  private MachineEntity findMachineByIdOrThrow(Long id) {
    return machineRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Mašina nije pronađena, id: " + id));
  }

  private void ensureMachineNameIsUniqueForUpdate(MachineEntity existingMachineEntity, String newName) {
    if (!existingMachineEntity.getName().equals(newName) && machineRepository.existsByName(newName)) {
      throw new RuntimeException("Mašina sa imenom '" + newName + "' već postoji");
    }
  }

  public void deleteMachine(Long id) {
    MachineEntity machineEntity = findMachineByIdOrThrow(id);
    machineRepository.delete(machineEntity);
  }
}
