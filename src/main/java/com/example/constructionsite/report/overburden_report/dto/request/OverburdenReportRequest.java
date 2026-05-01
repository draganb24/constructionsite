package com.example.constructionsite.report.overburden_report.dto.request;

import com.example.constructionsite.report.overburden_report_entry.dto.request.OverburdenReportEntryRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OverburdenReportRequest {
  @NotNull
  private LocalDate reportDate;
  private String description;
  private String note;
  private List<OverburdenReportEntryRequest> entries;
}
