package com.example.constructionsite.worker.mapper;

import com.example.constructionsite.worker.dto.request.CreateWorkerRequest;
import com.example.constructionsite.worker.dto.request.UpdateWorkerRequest;
import com.example.constructionsite.worker.dto.response.WorkerResponse;
import com.example.constructionsite.worker.entity.WorkerEntity;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface WorkerMapper {

  @Mapping(target = "active", constant = "true")
  WorkerEntity fromCreateRequestToEntity(CreateWorkerRequest request);

  void updateEntityFromUpdateRequest(UpdateWorkerRequest request,
                                     @MappingTarget WorkerEntity entity);

  @Mapping(target = "isActive", source = "active")
  WorkerResponse fromEntityToResponse(WorkerEntity entity);
}
