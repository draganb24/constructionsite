package com.example.constructionsite.report.overburden_report.service;

import com.example.constructionsite.report.overburden_report.dto.request.CreateOverburdenReportRequest;
import com.example.constructionsite.report.overburden_report.dto.request.SearchReportQuery;
import com.example.constructionsite.report.overburden_report.dto.request.UpdateOverburdenReportRequest;
import com.example.constructionsite.report.overburden_report.entity.OverburdenReportEntity;
import com.example.constructionsite.report.overburden_report.mapper.OverburdenReportMapper;
import com.example.constructionsite.report.overburden_report.repository.OverburdenReportRepository;
import com.example.constructionsite.report.overburden_report.specification.OverburdenReportFilterSpecification;
import com.example.constructionsite.report.overburden_report_entry.entity.OverburdenReportEntryEntity;
import com.example.constructionsite.report.overburden_report_entry.mapper.OverburdenReportEntryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class OverburdenReportService {

  private final OverburdenReportRepository overburdenReportRepository;
  private final OverburdenReportMapper reportMapper;
  private final OverburdenReportEntryMapper entryMapper;

  public Page<OverburdenReportEntity> findWithFilters(SearchReportQuery query, Pageable pageable) {
    return overburdenReportRepository.findAll(new OverburdenReportFilterSpecification(query),
        pageable);
  }

  public OverburdenReportEntity findById(Integer id) {
    return findByIdOrThrow(id);
  }

  @Transactional
  public OverburdenReportEntity create(CreateOverburdenReportRequest request) {
    OverburdenReportEntity report = reportMapper.fromCreateRequestToEntity(request);

    if (request.getEntries() != null) {
      request.getEntries().stream()
          .map(entryMapper::fromCreateRequestToEntity)
          .forEach(report::addEntry);
    }

    OverburdenReportEntity saved = overburdenReportRepository.save(report);

    return findByIdOrThrow(saved.getId());
  }

  @Transactional
  public OverburdenReportEntity update(Integer id, UpdateOverburdenReportRequest request) {
    OverburdenReportEntity entity = findByIdOrThrow(id);
    reportMapper.updateEntityFromUpdateRequest(request, entity);

    entity.getEntries().clear();

    if (request.getEntries() != null) {
      request.getEntries().stream()
          .map(entry -> {
            OverburdenReportEntryEntity entityEntry = new OverburdenReportEntryEntity();
            entryMapper.updateEntityFromUpdateRequest(entry, entityEntry);

            return entityEntry;
          }).forEach(entity::addEntry);
    }

    return findById(entity.getId());
  }

  @Transactional
  public void delete(Integer id) {
    OverburdenReportEntity report = findByIdOrThrow(id);
    overburdenReportRepository.delete(report);
  }

  private OverburdenReportEntity findByIdOrThrow(Integer id) {
    return overburdenReportRepository.findByIdWithEntries(id)
        .orElseThrow(() -> new RuntimeException("Izvještaj nije pronađen, id: " + id));
  }
}