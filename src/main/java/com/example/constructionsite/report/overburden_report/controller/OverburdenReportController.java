package com.example.constructionsite.report.overburden_report.controller;

import com.example.constructionsite.report.overburden_report.dto.request.OverburdenReportRequest;
import com.example.constructionsite.report.overburden_report.dto.response.OverburdenReportResponse;
import com.example.constructionsite.report.overburden_report.service.OverburdenReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports/overburden")
@CrossOrigin(origins = "http://localhost:4200")
public class OverburdenReportController {

  private final OverburdenReportService overburdenReportService;

  @GetMapping
  public ResponseEntity<Page<OverburdenReportResponse>> getAll(
      @RequestParam(required = false) LocalDate startDate,
      @RequestParam(required = false) LocalDate endDate,
      @RequestParam(required = false) Long machineId,
      @RequestParam(required = false) Long workerId,
      @PageableDefault(
          size = 10,
          sort = "reportDate",
          direction = Sort.Direction.DESC
      ) Pageable pageable) {

    return ResponseEntity.ok(
        overburdenReportService.findWithFilters(startDate, endDate, machineId, workerId, pageable)
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<OverburdenReportResponse> getById(@PathVariable Integer id) {
    return ResponseEntity.ok(overburdenReportService.findOverburdenReportById(id));
  }

  @PostMapping
  public ResponseEntity<OverburdenReportResponse> create(
      @Valid @RequestBody OverburdenReportRequest overburdenReportRequest) {
    return ResponseEntity.ok(overburdenReportService.createOverburdenReport(overburdenReportRequest));
  }

  @PutMapping("/{id}")
  public ResponseEntity<OverburdenReportResponse> update(@PathVariable Integer id,
                                                         @Valid @RequestBody OverburdenReportRequest overburdenReportRequest) {
    return ResponseEntity.ok(overburdenReportService.updateOverburdenReport(id, overburdenReportRequest));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    overburdenReportService.deleteOverburdenReport(id);
    return ResponseEntity.noContent().build();
  }

}
