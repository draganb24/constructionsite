package com.example.constructionsite.machine.service;

import com.example.constructionsite.machine.entity.MachineEntity;
import com.example.constructionsite.machine.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MachineService {

  private final MachineRepository machineRepository;

  public Page<MachineEntity> findAll(Pageable pageable) {
    return machineRepository.findAllActive(pageable);
  }

  public MachineEntity findById(Integer id) {
    return findByIdOrThrow(id);
  }

  @Transactional
  public MachineEntity create(MachineEntity machineEntity) {
    ensureNameIsUnique(machineEntity.getName());

    return machineRepository.save(machineEntity);
  }

  @Transactional
  public MachineEntity update(Integer id, MachineEntity updatedEntity) {
    MachineEntity existingEntity = findByIdOrThrow(id);
    ensureNameIsUniqueForUpdate(existingEntity, updatedEntity.getName());
    existingEntity.setName(updatedEntity.getName());

    return machineRepository.save(existingEntity);
  }

  @Transactional
  public void delete(Integer id) {
    MachineEntity machineEntity = findByIdOrThrow(id);
    machineEntity.setActive(false);
  }

  private MachineEntity findByIdOrThrow(Integer id) {
    return machineRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Mašina nije pronađena, id: " + id));
  }

  private void ensureNameIsUnique(String name) {
    if (machineRepository.existsByName(name)) {
      throw new RuntimeException("Mašina sa imenom '" + name + "' već postoji");
    }
  }

  private void ensureNameIsUniqueForUpdate(MachineEntity existingEntity,
                                           String newName) {
    if (!existingEntity.getName().equals(newName)
        && machineRepository.existsByName(newName)) {
      throw new RuntimeException("Mašina sa imenom '" + newName + "' već postoji");
    }
  }
}