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
  @GeneratedValue(generator = "worker_id_seq", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "worker_id_seq",
      sequenceName = "worker_id_seq",
      allocationSize = 1
  )
  private Integer id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "is_active", nullable = false)
  private boolean isActive = true;

  public String getFullName() {
    return Stream.of(firstName, lastName)
        .filter(Objects::nonNull)
        .map(String::trim)
        .collect(Collectors.joining(" "));
  }
}

