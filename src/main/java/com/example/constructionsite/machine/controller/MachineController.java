package com.example.constructionsite.machine.controller;

import com.example.constructionsite.machine.dto.request.CreateMachineRequest;
import com.example.constructionsite.machine.dto.request.UpdateMachineRequest;
import com.example.constructionsite.machine.dto.response.MachineResponse;
import com.example.constructionsite.machine.entity.MachineEntity;
import com.example.constructionsite.machine.mapper.MachineMapper;
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
  private final MachineMapper machineMapper;

  @GetMapping
  ResponseEntity<Page<MachineResponse>> getAll(@PageableDefault(sort = "id")
                                               Pageable pageable) {
    Page<MachineResponse> machines = machineService.findAll(pageable)
        .map(machineMapper::fromEntityToResponse);

    return ResponseEntity.ok(machines);
  }

  @GetMapping("/{id}")
  ResponseEntity<MachineResponse> getById(@PathVariable Integer id) {
    MachineEntity entity = machineService.findById(id);
    MachineResponse machine = machineMapper.fromEntityToResponse(entity);

    return ResponseEntity.ok(machine);
  }

  @PostMapping
  ResponseEntity<MachineResponse> create(@Valid @RequestBody CreateMachineRequest request) {
    MachineEntity entity = machineMapper.fromCreateRequestToEntity(request);
    MachineEntity savedEntity = machineService.create(entity);
    MachineResponse machine = machineMapper.fromEntityToResponse(savedEntity);

    return ResponseEntity.status(HttpStatus.CREATED).body(machine);
  }

  @PutMapping("/{id}")
  ResponseEntity<MachineResponse> update(@PathVariable Integer id,
                                         @Valid @RequestBody UpdateMachineRequest request) {
    MachineEntity existingEntity = machineService.findById(id);
    machineMapper.updateEntityFromUpdateRequest(request, existingEntity);
    MachineEntity updatedEntity = machineService.update(id, existingEntity);
    MachineResponse machine = machineMapper.fromEntityToResponse(updatedEntity);

    return ResponseEntity.ok(machine);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable Integer id) {
    machineService.delete(id);

    return ResponseEntity.noContent().build();
  }
}