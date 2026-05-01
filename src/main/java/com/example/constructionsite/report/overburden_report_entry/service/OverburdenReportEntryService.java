package com.example.constructionsite.report.overburden_report_entry.service;

import com.example.constructionsite.report.overburden_report_entry.entity.OverburdenReportEntryEntity;
import com.example.constructionsite.report.overburden_report_entry.repository.OverburdenReportEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OverburdenReportEntryService {

  private final OverburdenReportEntryRepository overburdenReportEntryRepository;

  public OverburdenReportEntryEntity saveEntry(OverburdenReportEntryEntity overburdenReportEntryEntity) {
    return overburdenReportEntryRepository.save(overburdenReportEntryEntity);
  }
}
