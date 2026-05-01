package com.example.constructionsite.worker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkerResponse {
  private Long id;
  private String firstName;
  private String lastName;
}
