package com.example.constructionsite.report.overburden_report_entry.mapper;

import com.example.constructionsite.report.overburden_report_entry.dto.request.CreateOverburdenReportEntryRequest;
import com.example.constructionsite.report.overburden_report_entry.dto.request.UpdateOverburdenReportEntryRequest;
import com.example.constructionsite.report.overburden_report_entry.dto.response.OverburdenReportEntryResponse;
import com.example.constructionsite.report.overburden_report_entry.entity.OverburdenReportEntryEntity;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Mapper(
    componentModel = "spring",
    uses = {
        WorkerReferenceMapper.class,
        MachineReferenceMapper.class
    },
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OverburdenReportEntryMapper {

  @Mapping(target = "workerEntity", source = "workerId")
  @Mapping(target = "machineEntity", source = "machineId")
  OverburdenReportEntryEntity fromCreateRequestToEntity(CreateOverburdenReportEntryRequest request);

  @Mapping(target = "workerEntity", source = "workerId")
  @Mapping(target = "machineEntity", source = "machineId")
  void updateEntityFromUpdateRequest(UpdateOverburdenReportEntryRequest request,
                                     @MappingTarget OverburdenReportEntryEntity entity);

  @Mapping(target = "workerId", source = "workerEntity.id")
  @Mapping(target = "workerName", source = "workerEntity.fullName")
  @Mapping(target = "machineId", source = "machineEntity.id")
  @Mapping(target = "machineName", source = "machineEntity.name")
  @Mapping(target = "totalWorkTime", expression = "java(calculateWorkTime(entity))")
  @Mapping(target = "machineHours", expression = "java(calculateMachineWorkHours(entity))")
  @Mapping(target = "mhPerTone", expression = "java(calculateMhPerTone(entity))")
  OverburdenReportEntryResponse fromEntityToResponse(OverburdenReportEntryEntity entity);

  default String calculateWorkTime(OverburdenReportEntryEntity entity) {
    if (entity.getStartWorkTime() == null || entity.getEndWorkTime() == null) {
      return null;
    }

    Duration duration = Duration.between(
        entity.getStartWorkTime(),
        entity.getEndWorkTime()
    );

    double hours = duration.toMinutes() / 60.0;

    return BigDecimal.valueOf(hours)
        .stripTrailingZeros()
        .toPlainString();
  }

  default String calculateMachineWorkHours(OverburdenReportEntryEntity entity) {
    if (entity.getStartWorkTimeGPRS() == null || entity.getEndWorkTimeGPRS() == null) {
      return null;
    }

    Duration duration = Duration.between(
        entity.getStartWorkTimeGPRS(),
        entity.getEndWorkTimeGPRS()
    );

    double hours = duration.toMinutes() / 60.0;

    return BigDecimal.valueOf(hours)
        .stripTrailingZeros()
        .toPlainString();
  }

  default String calculateMhPerTone(OverburdenReportEntryEntity entity) {
    if (entity.getStartWorkTimeGPRS() == null ||
        entity.getEndWorkTimeGPRS() == null ||
        entity.getTons() == null ||
        entity.getTons().doubleValue() == 0) {
      return "0";
    }

    double machineHours = Duration.between(
        entity.getStartWorkTimeGPRS(),
        entity.getEndWorkTimeGPRS()
    ).toMinutes() / 60.0;

    double tons = entity.getTons().doubleValue();

    double result = machineHours / tons;

    return BigDecimal.valueOf(result)
        .setScale(4, RoundingMode.HALF_UP)
        .stripTrailingZeros()
        .toPlainString();
  }
}