package com.example.constructionsite.machine.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "machines",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_machine_name", columnNames = "name")
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;
}
