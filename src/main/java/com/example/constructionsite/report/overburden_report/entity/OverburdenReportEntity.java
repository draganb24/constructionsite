package com.example.constructionsite.report.overburden_report.entity;

import com.example.constructionsite.report.overburden_report_entry.entity.OverburdenReportEntryEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "overburden_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OverburdenReportEntity {
  @Id
  @GeneratedValue(generator = "overburden_report_id_seq", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "overburden_report_id_seq",
      sequenceName = "overburden_report_id_seq",
      allocationSize = 1
  )
  private Integer id;

  @Column(name = "report_date", nullable = false)
  private LocalDate reportDate;

  @Column(name = "description", length = 255)
  private String description;

  @Column(name = "note", length = 500)
  private String note;

  @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<OverburdenReportEntryEntity> entries = new ArrayList<>();
}
