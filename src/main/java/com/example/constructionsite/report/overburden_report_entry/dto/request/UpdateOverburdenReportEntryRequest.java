package com.example.constructionsite.report.overburden_report_entry.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class UpdateOverburdenReportEntryRequest {
  private Integer workerId;
  private Integer machineId;
  private Integer tripCount;
  private BigDecimal kilometers;
  private BigDecimal machineHours;
  private BigDecimal fuelConsumption;
  private BigDecimal tons;
  private BigDecimal kmPerTone;
  private BigDecimal mhPerTone;
  private String stdRange;
  private String note;
  @JsonFormat(pattern = "HH:mm")
  private LocalTime startWorkTime;
  @JsonFormat(pattern = "HH:mm")
  private LocalTime endWorkTime;
  @JsonFormat(pattern = "HH:mm")
  private LocalTime startWorkTimeGPRS;
  @JsonFormat(pattern = "HH:mm")
  private LocalTime endWorkTimeGPRS;
}
