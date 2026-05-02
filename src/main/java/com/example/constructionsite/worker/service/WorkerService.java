package com.example.constructionsite.worker.service;

import com.example.constructionsite.worker.dto.request.WorkerRequest;
import com.example.constructionsite.worker.dto.response.WorkerResponse;
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

  public Page<WorkerResponse> findAll(Pageable pageable) {
    return workerRepository.findAllActive(pageable)
        .map(this::buildResponse);
  }

  private WorkerResponse buildResponse(WorkerEntity workerEntity) {
    return WorkerResponse.builder()
        .id(workerEntity.getId())
        .firstName(workerEntity.getFirstName())
        .lastName(workerEntity.getLastName())
        .isActive(workerEntity.isActive())
        .build();
  }

  public WorkerResponse findById(Integer id) {
    WorkerEntity workerEntity = findByIdOrThrow(id);
    return buildResponse(workerEntity);
  }

  private WorkerEntity findByIdOrThrow(Integer id) {
    return workerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Radnik nije pronađen, id: " + id));
  }

  public WorkerResponse create(WorkerRequest workerRequest) {
    ensureNameIsUnique(workerRequest.getFirstName(), workerRequest.getLastName());
    WorkerEntity workerEntity = buildEntity(workerRequest);
    WorkerEntity savedWorkerEntity = workerRepository.save(workerEntity);
    return buildResponse(savedWorkerEntity);
  }

  private void ensureNameIsUnique(String firstName, String lastName) {
    if (workerRepository.existsByFirstNameAndLastName(firstName, lastName)) {
      throw new RuntimeException("Radnik '" + firstName + " " + lastName + "' već postoji");
    }
  }

  private WorkerEntity buildEntity(WorkerRequest request) {
    WorkerEntity workerEntity = new WorkerEntity();
    workerEntity.setFirstName(request.getFirstName());
    workerEntity.setLastName(request.getLastName());
    return workerEntity;
  }

  public WorkerResponse update(Integer id, WorkerRequest workerRequest) {
    WorkerEntity existingWorkerEntity = findByIdOrThrow(id);
    ensureNameIsUniqueForUpdate(existingWorkerEntity, workerRequest.getFirstName(), workerRequest.getLastName());
    existingWorkerEntity.setFirstName(workerRequest.getFirstName());
    existingWorkerEntity.setLastName(workerRequest.getLastName());
    WorkerEntity savedWorkerEntity = workerRepository.save(existingWorkerEntity);
    return buildResponse(savedWorkerEntity);
  }

  private void ensureNameIsUniqueForUpdate(WorkerEntity existingWorkerEntity, String newFirstName, String newLastName) {
    boolean nameChanged = !existingWorkerEntity.getFirstName().equals(newFirstName)
        || !existingWorkerEntity.getLastName().equals(newLastName);
    if (nameChanged && workerRepository.existsByFirstNameAndLastName(newFirstName, newLastName)) {
      throw new RuntimeException("Radnik '" + newFirstName + " " + newLastName + "' već postoji");
    }
  }

  public void delete(Integer id) {
    WorkerEntity workerEntity = findByIdOrThrow(id);
    workerEntity.setActive(false);
    workerRepository.save(workerEntity);
  }
}
