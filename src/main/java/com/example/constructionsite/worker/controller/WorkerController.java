package com.example.constructionsite.worker.controller;

import com.example.constructionsite.worker.dto.request.WorkerRequest;
import com.example.constructionsite.worker.dto.response.WorkerResponse;
import com.example.constructionsite.worker.service.WorkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workers")
@CrossOrigin(origins = "http://localhost:4200")
public class WorkerController {

  private final WorkerService workerService;

  @GetMapping
  public ResponseEntity<Page<WorkerResponse>> getAll(
      @PageableDefault(size = 10, sort = "id") Pageable pageable) {
    return ResponseEntity.ok(workerService.findAllWorkers(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<WorkerResponse> getById(@PathVariable Long id) {
    return ResponseEntity.ok(workerService.findWorkerById(id));
  }

  @PostMapping
  public ResponseEntity<WorkerResponse> create(@Valid @RequestBody WorkerRequest workerRequest) {
    return ResponseEntity.ok(workerService.createWorker(workerRequest));
  }

  @PutMapping("/{id}")
  public ResponseEntity<WorkerResponse> update(
      @PathVariable Long id,
      @Valid @RequestBody WorkerRequest workerRequest) {
    return ResponseEntity.ok(workerService.updateWorker(id, workerRequest));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    workerService.deleteWorker(id);
    return ResponseEntity.noContent().build();
  }
}
