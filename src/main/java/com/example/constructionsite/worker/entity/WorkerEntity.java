package com.example.constructionsite.worker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(
    name = "workers",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_worker_fullname", columnNames = {"first_name", "last_name"})
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  public String getFullName() {
    return Stream.of(firstName, lastName)
        .filter(Objects::nonNull)
        .map(String::trim)
        .collect(Collectors.joining(" "));
  }
}

