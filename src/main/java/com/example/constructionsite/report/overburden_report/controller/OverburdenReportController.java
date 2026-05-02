package com.example.constructionsite.report.overburden_report.controller;

import com.example.constructionsite.report.overburden_report.dto.request.OverburdenReportRequest;
import com.example.constructionsite.report.overburden_report.dto.request.SearchReportQuery;
import com.example.constructionsite.report.overburden_report.dto.response.OverburdenReportResponse;
import com.example.constructionsite.report.overburden_report.service.OverburdenReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports/overburden")
@CrossOrigin(origins = "http://localhost:4200")
public class OverburdenReportController {

  private final OverburdenReportService overburdenReportService;

  @GetMapping
  ResponseEntity<Page<OverburdenReportResponse>> getAll(
      SearchReportQuery query,
      @PageableDefault(
          size = 10,
          sort = "reportDate",
          direction = Sort.Direction.DESC
      ) Pageable pageable) {
    Page<OverburdenReportResponse> response = overburdenReportService.findWithFilters(query, pageable);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  ResponseEntity<OverburdenReportResponse> getById(@PathVariable Integer id) {
    OverburdenReportResponse response = overburdenReportService.findById(id);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  ResponseEntity<OverburdenReportResponse> create(
      @Valid @RequestBody OverburdenReportRequest overburdenReportRequest) {
    OverburdenReportResponse response = overburdenReportService.create(overburdenReportRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  ResponseEntity<OverburdenReportResponse> update(@PathVariable Integer id,
                                                  @Valid @RequestBody OverburdenReportRequest overburdenReportRequest) {
    OverburdenReportResponse response = overburdenReportService.update(id, overburdenReportRequest);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable Integer id) {
    overburdenReportService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
