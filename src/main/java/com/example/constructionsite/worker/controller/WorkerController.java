package com.example.constructionsite.worker.controller;

import com.example.constructionsite.worker.dto.request.WorkerRequest;
import com.example.constructionsite.worker.dto.response.WorkerResponse;
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

  @GetMapping
  ResponseEntity<Page<WorkerResponse>> getAll(
      @PageableDefault(size = 10, sort = "id") Pageable pageable) {
    Page<WorkerResponse> response = workerService.findAll(pageable);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  ResponseEntity<WorkerResponse> getById(@PathVariable Integer id) {
    WorkerResponse response = workerService.findById(id);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  ResponseEntity<WorkerResponse> create(@Valid @RequestBody WorkerRequest workerRequest) {
    WorkerResponse response = workerService.create(workerRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  ResponseEntity<WorkerResponse> update(@PathVariable Integer id,
                                        @Valid @RequestBody WorkerRequest workerRequest) {
    WorkerResponse response = workerService.update(id, workerRequest);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable Integer id) {
    workerService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
