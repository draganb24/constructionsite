package com.example.constructionsite.report.overburden_report_entry.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@Builder
public class OverburdenReportEntryResponse {
  private Long id;
  private Long workerId;
  private String workerName;
  private Long machineId;
  private String machineName;
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
  private Long totalWorkTime;
  @JsonFormat(pattern = "HH:mm")
  private LocalTime startWorkTimeGPRS;
  @JsonFormat(pattern = "HH:mm")
  private LocalTime endWorkTimeGPRS;
}
