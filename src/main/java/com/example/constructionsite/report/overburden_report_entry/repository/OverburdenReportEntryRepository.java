package com.example.constructionsite.report.overburden_report_entry.repository;

import com.example.constructionsite.report.overburden_report_entry.entity.OverburdenReportEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverburdenReportEntryRepository extends JpaRepository<OverburdenReportEntryEntity, Long> {
}
