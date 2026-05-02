package com.example.constructionsite.report.overburden_report.service;

import com.example.constructionsite.machine.entity.MachineEntity;
import com.example.constructionsite.machine.repository.MachineRepository;
import com.example.constructionsite.report.overburden_report.dto.request.OverburdenReportRequest;
import com.example.constructionsite.report.overburden_report.dto.request.SearchReportQuery;
import com.example.constructionsite.report.overburden_report.dto.response.OverburdenReportResponse;
import com.example.constructionsite.report.overburden_report.entity.OverburdenReportEntity;
import com.example.constructionsite.report.overburden_report.repository.OverburdenReportRepository;
import com.example.constructionsite.report.overburden_report.specification.OverburdenReportFilterSpecification;
import com.example.constructionsite.report.overburden_report_entry.dto.request.OverburdenReportEntryRequest;
import com.example.constructionsite.report.overburden_report_entry.dto.response.OverburdenReportEntryResponse;
import com.example.constructionsite.report.overburden_report_entry.entity.OverburdenReportEntryEntity;
import com.example.constructionsite.worker.entity.WorkerEntity;
import com.example.constructionsite.worker.repository.WorkerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OverburdenReportService {

  private final OverburdenReportRepository overburdenReportRepository;
  private final WorkerRepository workerRepository;
  private final MachineRepository machineRepository;

  public Page<OverburdenReportResponse> findWithFilters(
      SearchReportQuery query,
      Pageable pageable
  ) {

    return overburdenReportRepository.findAll(
            new OverburdenReportFilterSpecification(query),
            pageable
        )
        .map(this::buildResponse);
  }

  private OverburdenReportResponse buildResponse(OverburdenReportEntity report) {
    List<OverburdenReportEntryResponse> entryResponses = report.getEntries().stream()
        .map(this::buildOverburdenEntryResponse)
        .collect(Collectors.toList());

    return OverburdenReportResponse.builder()
        .id(report.getId())
        .reportDate(report.getReportDate())
        .description(report.getDescription())
        .note(report.getNote())
        .entries(entryResponses)
        .build();
  }

  private OverburdenReportEntryResponse buildOverburdenEntryResponse(OverburdenReportEntryEntity entry) {

    Long totalWorkTime = calculateWorkingHours(
        entry.getStartWorkTime(),
        entry.getEndWorkTime()
    );

    BigDecimal machineHoursFromGPRS = calculateWorkingHoursGPRS(
        entry.getStartWorkTimeGPRS(),
        entry.getEndWorkTimeGPRS()
    );

    BigDecimal mhPerTone = calculateMhPerTone(
        machineHoursFromGPRS,
        entry.getTons()
    );

    return OverburdenReportEntryResponse.builder()
        .id(entry.getId())
        .workerId(entry.getWorkerEntity() != null ? entry.getWorkerEntity().getId() : null)
        .workerName(entry.getWorkerEntity() != null ? entry.getWorkerEntity().getFullName() : null)
        .machineId(entry.getMachineEntity() != null ? entry.getMachineEntity().getId() : null)
        .machineName(entry.getMachineEntity() != null ? entry.getMachineEntity().getName() : null)
        .tripCount(entry.getTripCount())
        .kilometers(entry.getKilometers())
        .machineHours(machineHoursFromGPRS)
        .fuelConsumption(entry.getFuelConsumption())
        .tons(entry.getTons())
        .kmPerTone(entry.getKmPerTone())
        .mhPerTone(mhPerTone)
        .stdRange(entry.getStdRange())
        .note(entry.getNote())
        .startWorkTime(entry.getStartWorkTime())
        .endWorkTime(entry.getEndWorkTime())
        .totalWorkTime(totalWorkTime)
        .startWorkTimeGPRS(entry.getStartWorkTimeGPRS())
        .endWorkTimeGPRS(entry.getEndWorkTimeGPRS())
        .build();
  }

  private Long calculateWorkingHours(LocalTime start, LocalTime end) {

    if (start == null || end == null) {
      return null;
    }

    if (end.isBefore(start)) {
      return Duration
          .between(start, end.plusHours(24))
          .toHours();
    }

    return Duration
        .between(start, end)
        .toHours();
  }

  private BigDecimal calculateWorkingHoursGPRS(LocalTime start, LocalTime end) {

    if (start == null || end == null) {
      return null;
    }

    Duration duration;

    if (end.isBefore(start)) {
      duration = Duration.between(start, end.plusHours(24));
    } else {
      duration = Duration.between(start, end);
    }

    long minutes = duration.toMinutes();

    return BigDecimal.valueOf(minutes)
        .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateMhPerTone(BigDecimal machineHours, BigDecimal tons) {

    if (machineHours == null
        || tons == null
        || tons.compareTo(BigDecimal.ZERO) <= 0) {
      return null;
    }

    return machineHours
        .divide(tons, 4, RoundingMode.HALF_UP);
  }

  public OverburdenReportResponse findById(Integer id) {
    OverburdenReportEntity overburdenReportEntity = findByIdOrThrow(id);
    return buildResponse(overburdenReportEntity);
  }

  private OverburdenReportEntity findByIdOrThrow(Integer id) {
    return overburdenReportRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Izvještaj nije pronađen, id: " + id));
  }

  public OverburdenReportResponse create(OverburdenReportRequest overburdenReportRequest) {
    OverburdenReportEntity overburdenReportEntity = buildEntity(overburdenReportRequest);
    return buildResponse(overburdenReportRepository.save(overburdenReportEntity));
  }

  private OverburdenReportEntity buildEntity(OverburdenReportRequest overburdenReportRequest) {
    OverburdenReportEntity report = new OverburdenReportEntity();
    report.setReportDate(overburdenReportRequest.getReportDate());
    report.setDescription(overburdenReportRequest.getDescription());
    report.setNote(overburdenReportRequest.getNote());

    if (overburdenReportRequest.getEntries() != null) {
      report.setEntries(overburdenReportRequest.getEntries().stream()
          .map(entryReq -> buildOverburdenEntryEntity(entryReq, report))
          .collect(Collectors.toList()));
    }

    return report;
  }

  private OverburdenReportEntryEntity buildOverburdenEntryEntity(OverburdenReportEntryRequest request,
                                                                 OverburdenReportEntity report) {
    WorkerEntity workerEntity = null;
    if (request.getWorkerId() != null) {
      workerEntity = workerRepository.findById(request.getWorkerId())
          .orElseThrow(() -> new RuntimeException("Radnik nije pronadjen, id: " + request.getWorkerId()));
    }

    MachineEntity machineEntity = null;
    if (request.getMachineId() != null) {
      machineEntity = machineRepository.findById(request.getMachineId())
          .orElseThrow(() -> new RuntimeException("Masina nije pronadjena, id: " + request.getMachineId()));
    }

    OverburdenReportEntryEntity entry = new OverburdenReportEntryEntity();
    entry.setReport(report);
    entry.setWorkerEntity(workerEntity);
    entry.setStartWorkTime(request.getStartWorkTime());
    entry.setEndWorkTime(request.getEndWorkTime());
    entry.setMachineEntity(machineEntity);
    entry.setStartWorkTimeGPRS(request.getStartWorkTimeGPRS());
    entry.setEndWorkTimeGPRS(request.getEndWorkTimeGPRS());
    entry.setTripCount(request.getTripCount());
    entry.setKilometers(request.getKilometers());
    entry.setMachineHours(request.getMachineHours());
    entry.setFuelConsumption(request.getFuelConsumption());
    entry.setTons(request.getTons());
    entry.setKmPerTone(request.getKmPerTone());
    entry.setMhPerTone(request.getMhPerTone());
    entry.setStdRange(request.getStdRange());
    entry.setNote(request.getNote());

    return entry;
  }

  public OverburdenReportResponse update(Integer id, OverburdenReportRequest request) {
    OverburdenReportEntity existingOverburdenReportEntity = findByIdOrThrow(id);

    existingOverburdenReportEntity.setReportDate(request.getReportDate());
    existingOverburdenReportEntity.setDescription(request.getDescription());
    existingOverburdenReportEntity.setNote(request.getNote());

    existingOverburdenReportEntity.getEntries().clear();

    if (request.getEntries() != null) {
      existingOverburdenReportEntity.getEntries().addAll(
          request.getEntries().stream()
              .map(entryRequest ->
                  buildOverburdenEntryEntity(entryRequest, existingOverburdenReportEntity))
              .toList()
      );
    }

    return buildResponse(overburdenReportRepository.save(existingOverburdenReportEntity));
  }

  public void delete(Integer id) {
    OverburdenReportEntity report = findByIdOrThrow(id);
    overburdenReportRepository.delete(report);
  }
}
