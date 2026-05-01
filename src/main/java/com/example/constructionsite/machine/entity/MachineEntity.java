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
  @GeneratedValue(generator = "machine_id_seq", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "machine_id_seq",
      sequenceName = "machine_id_seq",
      allocationSize = 1
  )
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(name = "is_active", nullable = false)
  private boolean isActive = true;
}
