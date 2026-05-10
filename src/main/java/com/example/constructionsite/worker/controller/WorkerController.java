package com.example.constructionsite.worker.controller;

import com.example.constructionsite.worker.dto.request.CreateWorkerRequest;
import com.example.constructionsite.worker.dto.request.UpdateWorkerRequest;
import com.example.constructionsite.worker.dto.response.WorkerResponse;
import com.example.constructionsite.worker.entity.WorkerEntity;
import com.example.constructionsite.worker.mapper.WorkerMapper;
import com.example.constructionsite.worker.service.WorkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workers")
@CrossOrigin(origins = "http://localhost:4200")
public class WorkerController {

  private final WorkerService workerService;
  private final WorkerMapper workerMapper;

  @GetMapping
  ResponseEntity<Page<WorkerResponse>> getAll(@PageableDefault(sort = "id")
                                              Pageable pageable) {
    Page<WorkerResponse> workers = workerService.findAll(pageable)
        .map(workerMapper::fromEntityToResponse);

    return ResponseEntity.ok(workers);
  }

  @GetMapping("/{id}")
  ResponseEntity<WorkerResponse> getById(@PathVariable Integer id) {
    WorkerEntity entity = workerService.findById(id);
    WorkerResponse worker = workerMapper.fromEntityToResponse(entity);

    return ResponseEntity.ok(worker);
  }

  @PostMapping
  ResponseEntity<WorkerResponse> create(@Valid @RequestBody CreateWorkerRequest request) {
    WorkerEntity entity = workerMapper.fromCreateRequestToEntity(request);
    WorkerEntity savedEntity = workerService.create(entity);
    WorkerResponse worker = workerMapper.fromEntityToResponse(savedEntity);

    return ResponseEntity.status(HttpStatus.CREATED).body(worker);
  }

  @PutMapping("/{id}")
  ResponseEntity<WorkerResponse> update(@PathVariable Integer id,
                                        @Valid @RequestBody UpdateWorkerRequest request) {
    WorkerEntity existingEntity = workerService.findById(id);
    workerMapper.updateEntityFromUpdateRequest(request, existingEntity);
    WorkerEntity updatedEntity = workerService.update(id, existingEntity);
    WorkerResponse worker = workerMapper.fromEntityToResponse(updatedEntity);

    return ResponseEntity.ok(worker);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable Integer id) {
    workerService.delete(id);

    return ResponseEntity.noContent().build();
  }
}