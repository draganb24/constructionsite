package com.example.constructionsite.machine.mapper;

import com.example.constructionsite.machine.dto.request.CreateMachineRequest;
import com.example.constructionsite.machine.dto.request.UpdateMachineRequest;
import com.example.constructionsite.machine.dto.response.MachineResponse;
import com.example.constructionsite.machine.entity.MachineEntity;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MachineMapper {

  @Mapping(target = "active", constant = "true")
  MachineEntity fromCreateRequestToEntity(CreateMachineRequest request);

  void updateEntityFromUpdateRequest(UpdateMachineRequest request,
                               @MappingTarget MachineEntity entity);

  @Mapping(target = "isActive", source = "active")
  MachineResponse fromEntityToResponse(MachineEntity entity);
}
