package com.example.constructionsite.report.overburden_report.repository;

import com.example.constructionsite.report.overburden_report.entity.OverburdenReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OverburdenReportRepository extends
    JpaRepository<OverburdenReportEntity, Integer>,
    JpaSpecificationExecutor<OverburdenReportEntity> {
}
