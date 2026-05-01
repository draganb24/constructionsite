package com.example.constructionsite.report.overburden_report.repository;

import com.example.constructionsite.report.overburden_report.entity.OverburdenReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OverburdenReportRepository extends JpaRepository<OverburdenReportEntity, Integer>,
    JpaSpecificationExecutor<OverburdenReportEntity> {

  @Query("""
          SELECT DISTINCT r
          FROM OverburdenReportEntity r
          JOIN r.entries e
          JOIN e.machineEntity m
          JOIN e.workerEntity w
          WHERE (:machineId IS NULL OR m.id = :machineId)
          AND (:workerId IS NULL OR w.id = :workerId)
          AND r.reportDate >= COALESCE(:startDate, r.reportDate)
          AND r.reportDate <= COALESCE(:endDate, r.reportDate)
      """)
  Page<OverburdenReportEntity> findWithFilters(
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("machineId") Long machineId,
      @Param("workerId") Long workerId,
      Pageable pageable
  );

}
