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
  @GeneratedValue(generator = "overburden_report_entry_id_seq", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "overburden_report_entry_id_seq",
      sequenceName = "overburden_report_entry_id_seq",
      allocationSize = 1
  )
  private Integer id;

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

  @Column(name = "trip_count")
  private Integer tripCount;

  @Column(name = "kilometers", precision = 10, scale = 2)
  private BigDecimal kilometers;

  @Column(name = "machine_hours", precision = 10, scale = 2)
  private BigDecimal machineHours;

  @Column(name = "fuel_consumption", precision = 10, scale = 2)
  private BigDecimal fuelConsumption;

  @Column(name = "tons", precision = 10, scale = 2)
  private BigDecimal tons;

  @Column(name = "km_per_tone", precision = 10, scale = 4)
  private BigDecimal kmPerTone;

  @Column(name = "mh_per_tone", precision = 10, scale = 4)
  private BigDecimal mhPerTone;

  @Column(name = "std_range")
  private String stdRange;

  @Column(name = "note")
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
