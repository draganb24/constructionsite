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

  public Page<WorkerResponse> findAllWorkers(Pageable pageable) {
    return workerRepository.findAll(pageable)
        .map(this::buildWorkerResponse);
  }

  private WorkerResponse buildWorkerResponse(WorkerEntity workerEntity) {
    return WorkerResponse.builder()
        .id(workerEntity.getId())
        .firstName(workerEntity.getFirstName())
        .lastName(workerEntity.getLastName())
        .build();
  }

  public WorkerResponse findWorkerById(Integer id) {
    WorkerEntity workerEntity = findWorkerByIdOrThrow(id);
    return buildWorkerResponse(workerEntity);
  }

  private WorkerEntity findWorkerByIdOrThrow(Integer id) {
    return workerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Radnik nije pronađen, id: " + id));
  }

  public WorkerResponse createWorker(WorkerRequest workerRequest) {
    ensureWorkerNameIsUnique(workerRequest.getFirstName(), workerRequest.getLastName());
    WorkerEntity workerEntity = buildWorkerEntity(workerRequest);
    WorkerEntity savedWorkerEntity = workerRepository.save(workerEntity);
    return buildWorkerResponse(savedWorkerEntity);
  }

  private void ensureWorkerNameIsUnique(String firstName, String lastName) {
    if (workerRepository.existsByFirstNameAndLastName(firstName, lastName)) {
      throw new RuntimeException("Radnik '" + firstName + " " + lastName + "' već postoji");
    }
  }

  private WorkerEntity buildWorkerEntity(WorkerRequest request) {
    WorkerEntity workerEntity = new WorkerEntity();
    workerEntity.setFirstName(request.getFirstName());
    workerEntity.setLastName(request.getLastName());
    return workerEntity;
  }

  public WorkerResponse updateWorker(Integer id, WorkerRequest workerRequest) {
    WorkerEntity existingWorkerEntity = findWorkerByIdOrThrow(id);
    ensureWorkerNameIsUniqueForUpdate(existingWorkerEntity, workerRequest.getFirstName(), workerRequest.getLastName());
    existingWorkerEntity.setFirstName(workerRequest.getFirstName());
    existingWorkerEntity.setLastName(workerRequest.getLastName());
    WorkerEntity savedWorkerEntity = workerRepository.save(existingWorkerEntity);
    return buildWorkerResponse(savedWorkerEntity);
  }

  private void ensureWorkerNameIsUniqueForUpdate(WorkerEntity existingWorkerEntity, String newFirstName, String newLastName) {
    boolean nameChanged = !existingWorkerEntity.getFirstName().equals(newFirstName)
        || !existingWorkerEntity.getLastName().equals(newLastName);
    if (nameChanged && workerRepository.existsByFirstNameAndLastName(newFirstName, newLastName)) {
      throw new RuntimeException("Radnik '" + newFirstName + " " + newLastName + "' već postoji");
    }
  }

  public void deleteWorker(Integer id) {
    WorkerEntity workerEntity = findWorkerByIdOrThrow(id);
    workerRepository.delete(workerEntity);
  }
}
