package com.example.constructionsite.report.overburden_report_entry.mapper;

import com.example.constructionsite.machine.entity.MachineEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MachineReferenceMapper {
  default MachineEntity map(Integer id) {
    if (id == null) {
      return null;
    }

    MachineEntity entity = new MachineEntity();
    entity.setId(id);

    return entity;
  }
}
