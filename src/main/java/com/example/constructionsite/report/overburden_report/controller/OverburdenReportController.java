package com.example.constructionsite.report.overburden_report.controller;

import com.example.constructionsite.report.overburden_report.dto.request.CreateOverburdenReportRequest;
import com.example.constructionsite.report.overburden_report.dto.request.SearchReportQuery;
import com.example.constructionsite.report.overburden_report.dto.request.UpdateOverburdenReportRequest;
import com.example.constructionsite.report.overburden_report.dto.response.OverburdenReportResponse;
import com.example.constructionsite.report.overburden_report.entity.OverburdenReportEntity;
import com.example.constructionsite.report.overburden_report.mapper.OverburdenReportMapper;
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

  private final OverburdenReportService service;
  private final OverburdenReportMapper mapper;

  @GetMapping
  ResponseEntity<Page<OverburdenReportResponse>> getAll(SearchReportQuery query,
                                                        @PageableDefault(sort = "reportDate",
                                                            direction = Sort.Direction.DESC)
                                                        Pageable pageable) {
    Page<OverburdenReportResponse> response = service.findWithFilters(query, pageable)
        .map(mapper::fromEntityToResponse);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  ResponseEntity<OverburdenReportResponse> getById(@PathVariable Integer id) {
    OverburdenReportEntity entity = service.findById(id);
    OverburdenReportResponse response = mapper.fromEntityToResponse(entity);

    return ResponseEntity.ok(response);
  }

  @PostMapping
  ResponseEntity<OverburdenReportResponse> create(@Valid @RequestBody CreateOverburdenReportRequest request) {
    OverburdenReportEntity entity = service.create(request);
    OverburdenReportResponse overburdenReport = mapper.fromEntityToResponse(entity);

    return ResponseEntity.status(HttpStatus.CREATED).body(overburdenReport);
  }

  @PutMapping("/{id}")
  ResponseEntity<OverburdenReportResponse> update(@PathVariable Integer id,
                                                  @Valid @RequestBody UpdateOverburdenReportRequest request) {
    OverburdenReportEntity updatedEntity = service.update(id, request);
    OverburdenReportResponse overburdenReport = mapper.fromEntityToResponse(updatedEntity);

    return ResponseEntity.ok(overburdenReport);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable Integer id) {
    service.delete(id);

    return ResponseEntity.noContent().build();
  }
}
