package com.example.constructionsite.machine.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MachineRequest {
  @NotBlank(message = "Ime masine je obavezno")
  private String name;
}
