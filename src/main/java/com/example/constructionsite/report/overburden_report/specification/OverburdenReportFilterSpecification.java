package com.example.constructionsite.report.overburden_report.specification;

import com.example.constructionsite.machine.entity.MachineEntity;
import com.example.constructionsite.machine.entity.MachineEntity_;
import com.example.constructionsite.report.overburden_report.dto.request.SearchReportQuery;
import com.example.constructionsite.report.overburden_report.entity.OverburdenReportEntity;
import com.example.constructionsite.report.overburden_report.entity.OverburdenReportEntity_;
import com.example.constructionsite.report.overburden_report_entry.entity.OverburdenReportEntryEntity;
import com.example.constructionsite.report.overburden_report_entry.entity.OverburdenReportEntryEntity_;
import com.example.constructionsite.worker.entity.WorkerEntity;
import com.example.constructionsite.worker.entity.WorkerEntity_;
import jakarta.persistence.criteria.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OverburdenReportFilterSpecification implements Specification<OverburdenReportEntity> {

  private final transient SearchReportQuery query;

  @Override
  public Predicate toPredicate(@NonNull Root<OverburdenReportEntity> root,
                               @NonNull CriteriaQuery<?> cq,
                               @NonNull CriteriaBuilder cb) {

    List<Predicate> predicates = new ArrayList<>();

    cq.distinct(true);

    filterByDateFrom(root, cb, predicates);
    filterByDateTo(root, cb, predicates);
    filterByMachine(root, cb, predicates);
    filterByWorker(root, cb, predicates);

    return cb.and(predicates.toArray(new Predicate[0]));
  }

  private void filterByDateFrom(Root<OverburdenReportEntity> root,
                                CriteriaBuilder cb,
                                List<Predicate> predicates) {

    if (query.startDate() != null) {
      predicates.add(
          cb.greaterThanOrEqualTo(
              root.get(OverburdenReportEntity_.reportDate),
              query.startDate()
          )
      );
    }
  }

  private void filterByDateTo(Root<OverburdenReportEntity> root,
                              CriteriaBuilder cb,
                              List<Predicate> predicates) {

    if (query.endDate() != null) {
      predicates.add(
          cb.lessThanOrEqualTo(
              root.get(OverburdenReportEntity_.reportDate),
              query.endDate()
          )
      );
    }
  }

  private void filterByMachine(Root<OverburdenReportEntity> root,
                               CriteriaBuilder cb,
                               List<Predicate> predicates) {

    if (query.machineId() != null) {

      Join<OverburdenReportEntity, OverburdenReportEntryEntity> entries =
          root.join(OverburdenReportEntity_.entries, JoinType.INNER);

      Join<OverburdenReportEntryEntity, MachineEntity> machine =
          entries.join(OverburdenReportEntryEntity_.machineEntity, JoinType.INNER);

      predicates.add(
          cb.equal(
              machine.get(MachineEntity_.id),
              query.machineId()
          )
      );
    }
  }

  private void filterByWorker(Root<OverburdenReportEntity> root,
                              CriteriaBuilder cb,
                              List<Predicate> predicates) {

    if (query.workerId() != null) {

      Join<OverburdenReportEntity, OverburdenReportEntryEntity> entries =
          root.join(OverburdenReportEntity_.entries, JoinType.INNER);

      Join<OverburdenReportEntryEntity, WorkerEntity> worker =
          entries.join(OverburdenReportEntryEntity_.workerEntity, JoinType.INNER);

      predicates.add(
          cb.equal(
              worker.get(WorkerEntity_.id),
              query.workerId()
          )
      );
    }
  }
}
