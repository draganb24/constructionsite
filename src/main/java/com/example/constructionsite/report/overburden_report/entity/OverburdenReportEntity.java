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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate reportDate;

  private String description;

  private String note;

  @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<OverburdenReportEntryEntity> entries = new ArrayList<>();
}
