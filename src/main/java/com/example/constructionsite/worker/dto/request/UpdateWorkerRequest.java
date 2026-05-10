package com.example.constructionsite.worker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateWorkerRequest {
  @NotBlank(message = "Ime je obavezno")
  private String firstName;

  @NotBlank(message = "Prezime je obavezno")
  private String lastName;
}