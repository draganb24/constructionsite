package com.example.constructionsite.report.overburden_report.dto.response;

import com.example.constructionsite.report.overburden_report_entry.dto.response.OverburdenReportEntryResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class OverburdenReportResponse {
  private Long id;
  private LocalDate reportDate;
  private String description;
  private String note;
  private List<OverburdenReportEntryResponse> entries;
}
