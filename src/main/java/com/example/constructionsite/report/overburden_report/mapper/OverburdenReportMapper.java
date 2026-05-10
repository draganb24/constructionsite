package com.example.constructionsite.report.overburden_report.mapper;

import com.example.constructionsite.report.overburden_report.dto.request.CreateOverburdenReportRequest;
import com.example.constructionsite.report.overburden_report.dto.request.UpdateOverburdenReportRequest;
import com.example.constructionsite.report.overburden_report.dto.response.OverburdenReportResponse;
import com.example.constructionsite.report.overburden_report.entity.OverburdenReportEntity;
import com.example.constructionsite.report.overburden_report_entry.mapper.OverburdenReportEntryMapper;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    uses = {
        OverburdenReportEntryMapper.class
    },
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OverburdenReportMapper {
  OverburdenReportEntity fromCreateRequestToEntity(CreateOverburdenReportRequest request);

  void updateEntityFromUpdateRequest(UpdateOverburdenReportRequest request,
                                     @MappingTarget OverburdenReportEntity entity);

  OverburdenReportResponse fromEntityToResponse(OverburdenReportEntity entity);
}