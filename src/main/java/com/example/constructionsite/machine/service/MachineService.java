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

  public Page<MachineResponse> findAll(Pageable pageable) {
    return machineRepository.findAllActive(pageable)
        .map(this::buildResponse);
  }

  public MachineResponse findById(Integer id) {
    MachineEntity machineEntity = machineRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Masina nije pronadjena, id: " + id));
    return buildResponse(machineEntity);
  }

  private MachineResponse buildResponse(MachineEntity machineEntity) {
    return MachineResponse.builder()
        .id(machineEntity.getId())
        .name(machineEntity.getName())
        .isActive(machineEntity.isActive())
        .build();
  }

  public MachineResponse create(MachineRequest machineRequest) {
    ensureNameIsUnique(machineRequest.getName());
    MachineEntity machineEntity = buildEntity(machineRequest);
    MachineEntity savedMachineEntity = machineRepository.save(machineEntity);
    return buildResponse(savedMachineEntity);
  }

  private void ensureNameIsUnique(String name) {
    if (machineRepository.existsByName(name)) {
      throw new RuntimeException("Mašina sa imenom '" + name + "' već postoji");
    }
  }

  private MachineEntity buildEntity(MachineRequest request) {
    MachineEntity machineEntity = new MachineEntity();
    machineEntity.setName(request.getName());
    return machineEntity;
  }

  public MachineResponse update(Integer id, MachineRequest machineRequest) {
    MachineEntity existingMachineEntity = findByIdOrThrow(id);
    ensureNameIsUniqueForUpdate(existingMachineEntity, machineRequest.getName());
    existingMachineEntity.setName(machineRequest.getName());
    MachineEntity savedMachineEntity = machineRepository.save(existingMachineEntity);
    return buildResponse(savedMachineEntity);
  }

  private MachineEntity findByIdOrThrow(Integer id) {
    return machineRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Mašina nije pronađena, id: " + id));
  }

  private void ensureNameIsUniqueForUpdate(MachineEntity existingMachineEntity, String newName) {
    if (!existingMachineEntity.getName().equals(newName) && machineRepository.existsByName(newName)) {
      throw new RuntimeException("Mašina sa imenom '" + newName + "' već postoji");
    }
  }

  public void delete(Integer id) {
    MachineEntity machineEntity = findByIdOrThrow(id);
    machineEntity.setActive(false);
    machineRepository.save(machineEntity);
  }
}
