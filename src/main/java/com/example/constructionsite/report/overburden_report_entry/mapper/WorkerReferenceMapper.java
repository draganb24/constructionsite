package com.example.constructionsite.report.overburden_report_entry.mapper;

import com.example.constructionsite.worker.entity.WorkerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkerReferenceMapper {
  default WorkerEntity map(Integer id) {
    if (id == null) {
      return null;
    }

    WorkerEntity entity = new WorkerEntity();
    entity.setId(id);

    return entity;
  }
}