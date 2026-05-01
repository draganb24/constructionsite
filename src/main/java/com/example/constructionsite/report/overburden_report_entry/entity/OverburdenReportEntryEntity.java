package com.example.constructionsite.report.overburden_report_entry.entity;

import com.example.constructionsite.machine.entity.MachineEntity;
import com.example.constructionsite.report.overburden_report.entity.OverburdenReportEntity;
import com.example.constructionsite.worker.entity.WorkerEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "overburden_report_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OverburdenReportEntryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(
      name = "report_id",
      foreignKey = @ForeignKey(name = "fk_overburden_entry_report")
  )
  @JsonBackReference
  private OverburdenReportEntity report;

  @ManyToOne
  @JoinColumn(
      name = "worker_id",
      foreignKey = @ForeignKey(name = "fk_overburden_entry_worker")
  )
  private WorkerEntity workerEntity;

  @ManyToOne
  @JoinColumn(
      name = "machine_id",
      foreignKey = @ForeignKey(name = "fk_overburden_entry_machine")

  )
  private MachineEntity machineEntity;

  private Integer tripCount;

  private BigDecimal kilometers;

  private BigDecimal machineHours;

  private BigDecimal fuelConsumption;

  private BigDecimal tons;

  private BigDecimal kmPerTone;

  private BigDecimal mhPerTone;

  private String stdRange;

  private String note;

  @Column(name = "start_work_time")
  private LocalTime startWorkTime;

  @Column(name = "end_work_time")
  private LocalTime endWorkTime;

  @Column(name = "start_work_time_gprs")
  private LocalTime startWorkTimeGPRS;

  @Column(name = "end_work_time_gprs")
  private LocalTime endWorkTimeGPRS;
}
