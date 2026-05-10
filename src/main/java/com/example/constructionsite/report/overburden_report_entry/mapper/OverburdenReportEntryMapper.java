package com.example.constructionsite.report.overburden_report_entry.mapper;

import com.example.constructionsite.report.overburden_report_entry.dto.request.CreateOverburdenReportEntryRequest;
import com.example.constructionsite.report.overburden_report_entry.dto.request.UpdateOverburdenReportEntryRequest;
import com.example.constructionsite.report.overburden_report_entry.dto.response.OverburdenReportEntryResponse;
import com.example.constructionsite.report.overburden_report_entry.entity.OverburdenReportEntryEntity;
import org.mapstruct.*;

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
  OverburdenReportEntryResponse fromEntityToResponse(OverburdenReportEntryEntity entity);
}