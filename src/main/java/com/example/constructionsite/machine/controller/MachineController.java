package com.example.constructionsite.machine.controller;

import com.example.constructionsite.machine.dto.request.MachineRequest;
import com.example.constructionsite.machine.dto.response.MachineResponse;
import com.example.constructionsite.machine.service.MachineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/machines")
public class MachineController {

  private final MachineService machineService;

  @GetMapping
  public ResponseEntity<Page<MachineResponse>> getAll(@PageableDefault(size = 10, sort = "id")
                                                      Pageable pageable) {
    return ResponseEntity.ok(machineService.findAllMachines(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MachineResponse> getById(@PathVariable Long id) {
    return ResponseEntity.ok(machineService.findMachineById(id));
  }

  @PostMapping
  public ResponseEntity<MachineResponse> create(@Valid @RequestBody MachineRequest machineRequest) {
    return ResponseEntity.ok(machineService.createMachine(machineRequest));
  }

  @PutMapping("/{id}")
  public ResponseEntity<MachineResponse> update(@PathVariable Long id, @RequestBody MachineRequest machineRequest) {
    return ResponseEntity.ok(machineService.updateMachine(id, machineRequest));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    machineService.deleteMachine(id);
    return ResponseEntity.noContent().build();
  }
}
