package com.example.constructionsite.machine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MachineResponse {
  private Integer id;
  private String name;
  private Boolean isActive;
}
