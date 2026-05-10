package com.example.constructionsite.worker.service;

import com.example.constructionsite.worker.entity.WorkerEntity;
import com.example.constructionsite.worker.repository.WorkerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkerService {

  private final WorkerRepository workerRepository;

  public Page<WorkerEntity> findAll(Pageable pageable) {
    return workerRepository.findAllActive(pageable);
  }

  public WorkerEntity findById(Integer id) {
    return findByIdOrThrow(id);
  }

  public WorkerEntity create(WorkerEntity workerEntity) {
    ensureNameIsUnique(workerEntity.getFirstName(), workerEntity.getLastName());

    return workerRepository.save(workerEntity);
  }

  public WorkerEntity update(Integer id, WorkerEntity updatedEntity) {
    WorkerEntity existingEntity = findByIdOrThrow(id);
    ensureNameIsUniqueForUpdate(existingEntity, updatedEntity.getFirstName(),
        updatedEntity.getLastName());
    existingEntity.setFirstName(updatedEntity.getFirstName());
    existingEntity.setLastName(updatedEntity.getLastName());

    return workerRepository.save(existingEntity);
  }

  public void delete(Integer id) {
    WorkerEntity workerEntity = findByIdOrThrow(id);
    workerEntity.setActive(false);
  }

  private WorkerEntity findByIdOrThrow(Integer id) {
    return workerRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Radnik nije pronađen, id: " + id));
  }

  private void ensureNameIsUnique(String firstName, String lastName) {
    if (workerRepository.existsByFirstNameAndLastName(firstName, lastName)) {
      throw new RuntimeException("Radnik '" + firstName + " " + lastName + "' već postoji");
    }
  }

  private void ensureNameIsUniqueForUpdate(WorkerEntity existingEntity, String newFirstName,
                                           String newLastName) {

    boolean nameChanged = !existingEntity.getFirstName().equals(newFirstName)
        || !existingEntity.getLastName().equals(newLastName);

    if (nameChanged && workerRepository.existsByFirstNameAndLastName(newFirstName, newLastName)) {
      throw new RuntimeException("Radnik '" + newFirstName + " " + newLastName + "' već postoji");
    }
  }
}