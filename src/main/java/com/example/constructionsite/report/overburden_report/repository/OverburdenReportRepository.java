package com.example.constructionsite.report.overburden_report.repository;

import com.example.constructionsite.report.overburden_report.entity.OverburdenReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OverburdenReportRepository extends JpaRepository<OverburdenReportEntity, Integer>,
    JpaSpecificationExecutor<OverburdenReportEntity> {
  @EntityGraph(attributePaths = {
      "entries",
      "entries.machineEntity",
      "entries.workerEntity"
  })
  @Query("""
      SELECT r
      FROM OverburdenReportEntity r
      WHERE (:machineId IS NULL OR EXISTS (
          SELECT 1
          FROM OverburdenReportEntryEntity e
          WHERE e.report = r AND e.machineEntity.id = :machineId
      ))
      AND (:workerId IS NULL OR EXISTS (
          SELECT 1
          FROM OverburdenReportEntryEntity e
          WHERE e.report = r AND e.workerEntity.id = :workerId
      ))
      AND (:startDate IS NULL OR r.reportDate >= :startDate)
      AND (:endDate IS NULL OR r.reportDate <= :endDate)
      """)
  Page<OverburdenReportEntity> findWithFilters(
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("machineId") Long machineId,
      @Param("workerId") Long workerId,
      Pageable pageable
  );

}
