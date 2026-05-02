package com.example.constructionsite.machine.controller;

import com.example.constructionsite.machine.dto.request.MachineRequest;
import com.example.constructionsite.machine.dto.response.MachineResponse;
import com.example.constructionsite.machine.service.MachineService;
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
@RequestMapping("/api/machines")
public class MachineController {

  private final MachineService machineService;

  @GetMapping
  ResponseEntity<Page<MachineResponse>> getAll(@PageableDefault(size = 10, sort = "id")
                                               Pageable pageable) {
    Page<MachineResponse> response = machineService.findAll(pageable);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  ResponseEntity<MachineResponse> getById(@PathVariable Integer id) {
    MachineResponse response = machineService.findById(id);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  ResponseEntity<MachineResponse> create(@Valid @RequestBody MachineRequest machineRequest) {
    MachineResponse response = machineService.create(machineRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  ResponseEntity<MachineResponse> update(@PathVariable Integer id,
                                         @RequestBody MachineRequest machineRequest) {
    MachineResponse response = machineService.update(id, machineRequest);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable Integer id) {
    machineService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
