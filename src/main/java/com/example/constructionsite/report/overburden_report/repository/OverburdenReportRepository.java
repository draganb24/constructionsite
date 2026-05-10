package com.example.constructionsite.report.overburden_report.repository;

import com.example.constructionsite.report.overburden_report.entity.OverburdenReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OverburdenReportRepository
    extends JpaRepository<OverburdenReportEntity, Integer>,
    JpaSpecificationExecutor<OverburdenReportEntity> {

  @Query("""
          select distinct r
          from OverburdenReportEntity r
          left join fetch r.entries e
          left join fetch e.workerEntity
          left join fetch e.machineEntity
          where r.id = :id
      """)
  Optional<OverburdenReportEntity> findByIdWithEntries(Integer id);
}